import { useMutation } from '@tanstack/react-query';

import { useToast } from '@hooks/common/useToast';
import { useTokenError } from '@hooks/member/useTokenError';

import { postImage } from '@api/image/postImage';
import type { ErrorResponseData } from '@api/interceptors';

import { ERROR_CODE, HTTP_STATUS_CODE } from '@constants/api';

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

      if (
        (error.code && error.code === ERROR_CODE.LARGE_IMAGE_FILE) ||
        error.statusCode === HTTP_STATUS_CODE.CONTENT_TOO_LARGE
      ) {
        createToast(
          '이미지 용량이 커서 업로드에 실패했습니다. 10mb 이하의 파일을 업로드해 주세요.'
        );

        return;
      }

      createToast('이미지 업로드에 실패했습니다. 잠시 후 다시 시도해 주세요.');
    },
  });

  return imageMutation;
};
