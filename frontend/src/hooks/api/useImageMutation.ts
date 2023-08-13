import { useMutation } from '@tanstack/react-query';

import { useToast } from '@hooks/common/useToast';
import { useTokenError } from '@hooks/member/useTokenError';

import { postImage } from '@api/image/postImage';
import type { ErrorResponseData } from '@api/interceptors';

import { ERROR_CODE } from '@constants/api';

export const useImageMutation = () => {
  const { createToast } = useToast();
  const { handleTokenError } = useTokenError();

  const imageMutation = useMutation({
    mutationFn: postImage,
    onError: (error: ErrorResponseData) => {
      if (error.code && error.code > ERROR_CODE.TOKEN_ERROR_RANGE) {
        handleTokenError();

        return;
      }

      createToast('이미지 업로드에 실패했습니다. 잠시 후 다시 시도해 주세요.', 'error');
    },
  });

  return imageMutation;
};
