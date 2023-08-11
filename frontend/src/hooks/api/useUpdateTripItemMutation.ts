import { ERROR_CODE } from '@constants/api';
import { toastListState } from '@store/toast';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useSetRecoilState } from 'recoil';

import { generateUniqueId } from '@utils/uniqueId';

import type { ErrorResponseData } from '@api/interceptors';
import { putTripItem } from '@api/tripItem/putTripItem';

import { useTokenError } from '@hooks/member/useTokenError';

export const useUpdateTripItemMutation = () => {
  const queryClient = useQueryClient();
  const setToastList = useSetRecoilState(toastListState);
  const { handleTokenError } = useTokenError();

  const updateTripItemMutation = useMutation({
    mutationFn: putTripItem,
    onSuccess: (_, { tripId }) => {
      queryClient.invalidateQueries({ queryKey: ['trip', tripId] });
    },
    onError: (error: ErrorResponseData) => {
      if (error.code && error.code > ERROR_CODE.TOKEN_ERROR_RANGE) {
        handleTokenError();

        return;
      }
      setToastList((prevToastList) => [
        ...prevToastList,
        {
          id: generateUniqueId(),
          variant: 'error',
          message: '아이템 수정에 실패했습니다. 잠시 후 다시 시도해 주세요.',
        },
      ]);
    },
  });

  return updateTripItemMutation;
};
