import { useMutation, useQueryClient } from '@tanstack/react-query';

import { useSetRecoilState } from 'recoil';

import { useTokenError } from '@hooks/member/useTokenError';

import { toastListState } from '@store/toast';

import { patchDayLogItemOrder } from '@api/dayLog/patchDayLogItemOrder';
import type { ErrorResponseData } from '@api/interceptors';

import { generateUniqueId } from '@utils/uniqueId';

import { ERROR_CODE } from '@constants/api';

export const useDayLogOrderMutation = () => {
  const queryClient = useQueryClient();
  const setToastList = useSetRecoilState(toastListState);

  const { handleTokenError } = useTokenError();

  const dayLogOrderMutation = useMutation({
    mutationFn: patchDayLogItemOrder,
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
          message: '아이템 순서 변경에 실패했습니다. 잠시 후 다시 시도해 주세요.',
        },
      ]);
    },
  });

  return dayLogOrderMutation;
};
