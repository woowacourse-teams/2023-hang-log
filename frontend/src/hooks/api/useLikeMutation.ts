import { useMutation } from '@tanstack/react-query';

import { useToast } from '@hooks/common/useToast';
import { useTokenError } from '@hooks/member/useTokenError';

import type { ErrorResponseData } from '@api/interceptors';
import { postLike } from '@api/trip/postLike';

import { ERROR_CODE } from '@constants/api';

export const useLikeMutation = () => {
  const { createToast } = useToast();
  const { handleTokenError } = useTokenError();

  const imageMutation = useMutation({
    mutationFn: postLike,
    onError: (error: ErrorResponseData) => {
      if (error.code === ERROR_CODE.UNAUTHORIZED) {
        createToast('로그인이 필요합니다.', 'default');
        return;
      }

      if (error.code && error.code > ERROR_CODE.TOKEN_ERROR_RANGE) {
        handleTokenError();
      }
      createToast('오류가 발생했습니다. 다시 시도해 주세요.');
    },
  });

  return imageMutation;
};
