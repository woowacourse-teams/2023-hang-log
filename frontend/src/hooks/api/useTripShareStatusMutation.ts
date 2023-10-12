import { useMutation, useQueryClient } from '@tanstack/react-query';

import { useToast } from '@hooks/common/useToast';
import { useTokenError } from '@hooks/member/useTokenError';

import type { ErrorResponseData } from '@api/interceptors';
import { patchTripSharedStatus } from '@api/trip/patchTripShareStatus';

import { ERROR_CODE } from '@constants/api';
import { TRIP_TYPE } from '@constants/trip';

export const useTripShareStatusMutation = () => {
  const queryClient = useQueryClient();
  const { createToast } = useToast();
  const { handleTokenError } = useTokenError();

  const tripShareStatusMutation = useMutation({
    mutationFn: patchTripSharedStatus,
    onSuccess: (_, { tripId }) => {
      queryClient.invalidateQueries({ queryKey: [TRIP_TYPE.PERSONAL, tripId] });
    },
    onError: (error: ErrorResponseData) => {
      if (error.code === ERROR_CODE.UNAUTHORIZED && error.message) {
        createToast(error.message, 'default');
        return;
      }

      if (error.code && error.code > ERROR_CODE.TOKEN_ERROR_RANGE) {
        handleTokenError();
      }

      createToast('여행 공유 설정 변경에 실패했습니다. 잠시 후 다시 시도해주세요.');
    },
  });

  return tripShareStatusMutation;
};
