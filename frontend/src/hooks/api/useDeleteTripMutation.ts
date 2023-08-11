import { ERROR_CODE } from '@constants/api';
import { toastListState } from '@store/toast';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useSetRecoilState } from 'recoil';

import { generateUniqueId } from '@utils/uniqueId';

import type { ErrorResponseData } from '@api/interceptors';
import { deleteTrip } from '@api/trip/deleteTrip';

import { useTokenError } from '@hooks/member/useTokenError';

export const useDeleteTripMutation = () => {
  const queryClient = useQueryClient();

  const setToastList = useSetRecoilState(toastListState);

  const { handleTokenError } = useTokenError();

  const deleteTripMutation = useMutation({
    mutationFn: deleteTrip,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['trips'] });
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
          message: '여행 삭제에 실패했습니다. 잠시 후 다시 시도해 주세요.',
        },
      ]);
    },
  });

  return deleteTripMutation;
};
