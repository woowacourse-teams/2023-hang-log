import { TRIP_ITEM_ADD_MAX_IMAGE_UPLOAD_COUNT } from '@constants/ui';
import type { ChangeEvent } from 'react';
import { useState } from 'react';

import { useImageMutation } from '@hooks/api/useImageMutation';

interface UseImageUploadParams {
  initialImageUrls: string[];
  maxUploadCount?: number;
  onSuccess?: CallableFunction;
  onError?: CallableFunction;
}

export const useImageUpload = ({
  initialImageUrls,
  maxUploadCount = TRIP_ITEM_ADD_MAX_IMAGE_UPLOAD_COUNT,
  onSuccess,
  onError,
}: UseImageUploadParams) => {
  const imageMutation = useImageMutation();
  const [uploadedImageUrls, setUploadedImageUrls] = useState(initialImageUrls);

  const handleImageUpload = async (event: ChangeEvent<HTMLInputElement>) => {
    if (!event.target.files) return;

    if (event.target.files.length + uploadedImageUrls.length > maxUploadCount) {
      onError?.('이미지는 최대 5개 업로드할 수 있습니다.');
      return;
    }

    const formData = new FormData();

    [...event.target.files].forEach((file) => {
      formData.append('images', file);
    });

    imageMutation.mutate(
      { images: formData },
      {
        onSuccess: (data) => {
          setUploadedImageUrls((prevImageUrls) => {
            const updatedImageUrls = [...prevImageUrls, ...data];
            onSuccess?.(updatedImageUrls);

            return updatedImageUrls;
          });
        },
        onError: () => onError?.('이미지 업로드를 실패했습니다. 잠시 후 다시 시도해 주세요.'),
      }
    );

    // eslint-disable-next-line no-param-reassign
    event.target.value = '';
  };

  const handleImageRemoval = (selectedImageUrl: string) => () => {
    setUploadedImageUrls((prevImageUrls) => {
      const updatedImageUrls = prevImageUrls.filter((imageUrl) => imageUrl !== selectedImageUrl);
      onSuccess?.(updatedImageUrls);

      return updatedImageUrls;
    });
  };

  return { uploadedImageUrls, handleImageUpload, handleImageRemoval };
};
