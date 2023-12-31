import { useMutation, useQueryClient } from '@tanstack/react-query';

import { useToast } from '@hooks/common/useToast';
import { useTokenError } from '@hooks/member/useTokenError';

import type { ErrorResponseData } from '@api/interceptors';
import { deleteTrip } from '@api/trip/deleteTrip';

import { ERROR_CODE } from '@constants/api';

export const useDeleteTripMutation = () => {
  const queryClient = useQueryClient();

  const { createToast } = useToast();
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

      createToast('여행 삭제에 실패했습니다. 잠시 후 다시 시도해 주세요.');
    },
  });

  return deleteTripMutation;
};
