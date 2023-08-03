import { toastListState } from '@store/toast';
import { useMutation } from '@tanstack/react-query';
import { useSetRecoilState } from 'recoil';

import { generateUniqueId } from '@utils/uniqueId';

import { postImage } from '@api/image/postImage';

export const useImageMutation = () => {
  const setToastList = useSetRecoilState(toastListState);

  const imageMutation = useMutation({
    mutationFn: postImage,
    onError: () => {
      setToastList((prevToastList) => [
        ...prevToastList,
        {
          id: generateUniqueId(),
          variant: 'error',
          message: '이미지 업로드를 실패했습니다. 잠시 후 다시 시도해 주세요.',
        },
      ]);
    },
  });

  return imageMutation;
};
