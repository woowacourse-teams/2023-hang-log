import { useMutation, useQueryClient } from '@tanstack/react-query';

import { useToast } from '@hooks/common/useToast';
import { useTokenError } from '@hooks/member/useTokenError';

import type { ErrorResponseData } from '@api/interceptors';
import { putTrip } from '@api/trip/putTrip';

import { ERROR_CODE } from '@constants/api';

export const useTripEditMutation = () => {
  const queryClient = useQueryClient();

  const { generateToast } = useToast();
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

      generateToast('여행 정보 변경에 실패했습니다. 잠시 후 다시 시도해주세요.', 'error');
    },
  });

  return tripMutation;
};
