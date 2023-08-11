import { ERROR_CODE } from '@constants/api';
import { toastListState } from '@store/toast';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useSetRecoilState } from 'recoil';

import { generateUniqueId } from '@utils/uniqueId';

import type { ErrorResponseData } from '@api/interceptors';
import { putTrip } from '@api/trip/putTrip';

import { useTokenError } from '@hooks/member/useTokenError';

export const useTripEditMutation = () => {
  const queryClient = useQueryClient();
  const setToastList = useSetRecoilState(toastListState);
  const { handleTokenError } = useTokenError();

  const tripMutation = useMutation({
    mutationFn: putTrip,
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
          message: '여행 정보 변경에 실패했습니다. 잠시 후 다시 시도해주세요.',
        },
      ]);
    },
  });

  return tripMutation;
};
