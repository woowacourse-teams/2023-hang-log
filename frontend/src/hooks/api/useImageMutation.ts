import { ERROR_CODE } from '@constants/api';
import { toastListState } from '@store/toast';
import { useMutation } from '@tanstack/react-query';
import { useSetRecoilState } from 'recoil';

import { generateUniqueId } from '@utils/uniqueId';

import { postImage } from '@api/image/postImage';
import type { ErrorResponseData } from '@api/interceptors';

import { useTokenError } from '@hooks/member/useTokenError';

export const useImageMutation = () => {
  const setToastList = useSetRecoilState(toastListState);

  const { handleTokenError } = useTokenError();

  const imageMutation = useMutation({
    mutationFn: postImage,
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
          message: '이미지 업로드에 실패했습니다. 잠시 후 다시 시도해 주세요.',
        },
      ]);
    },
  });

  return imageMutation;
};
