import { useMutation, useQueryClient } from '@tanstack/react-query';

import { useToast } from '@hooks/common/useToast';
import { useTokenError } from '@hooks/member/useTokenError';

import type { ErrorResponseData } from '@api/interceptors';
import { postLike } from '@api/trip/postLike';

import { ERROR_CODE } from '@constants/api';

export const useLikeMutation = () => {
  const queryClient = useQueryClient();
  const { createToast } = useToast();
  const { handleTokenError } = useTokenError();

  const likeMutation = useMutation({
    mutationFn: postLike,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['communityTrips', 1] });
      queryClient.invalidateQueries({ queryKey: ['recommendedTrips'] });
    },
    onError: (error: ErrorResponseData) => {
      if (error.code === ERROR_CODE.UNAUTHORIZED && error.message) {
        createToast(error.message, 'default');
        return;
      }

      if (error.code && error.code > ERROR_CODE.TOKEN_ERROR_RANGE) {
        handleTokenError();
      }
      createToast('오류가 발생했습니다. 다시 시도해 주세요.');
    },
  });

  return likeMutation;
};
