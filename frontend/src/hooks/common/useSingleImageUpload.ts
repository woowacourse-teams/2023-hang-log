import type { ChangeEvent } from 'react';
import { useCallback, useState } from 'react';

import { useImageMutation } from '@hooks/api/useImageMutation';

interface UseSingleImageUploadParams {
  initialImageUrl: string | null;
  onSuccess?: CallableFunction;
}

export const useSingleImageUpload = ({
  initialImageUrl,
  onSuccess,
}: UseSingleImageUploadParams) => {
  const imageMutation = useImageMutation();

  const [uploadedImageUrl, setUploadedImageUrl] = useState(initialImageUrl);

  const handleImageUpload = useCallback(
    async (event: ChangeEvent<HTMLInputElement>) => {
      const imageFiles = event.target.files;

      if (!imageFiles) return;

      const imageUploadFormData = new FormData();

      [...imageFiles].forEach((file) => {
        imageUploadFormData.append('images', file);
      });

      imageMutation.mutate(
        { images: imageUploadFormData },
        {
          onSuccess: ({ imageUrls }) => {
            setUploadedImageUrl(imageUrls[0]);
            onSuccess?.(imageUrls[0]);
          },
        }
      );

      // eslint-disable-next-line no-param-reassign
      event.target.value = '';
    },
    [imageMutation, onSuccess]
  );

  const handleImageRemoval = useCallback(() => {
    setUploadedImageUrl(null);
    onSuccess?.(null);
  }, [onSuccess]);

  return { uploadedImageUrl, handleImageUpload, handleImageRemoval };
};
