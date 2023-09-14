import type { ChangeEvent } from 'react';
import { useCallback, useState } from 'react';

import imageCompression from 'browser-image-compression';

import { useImageMutation } from '@hooks/api/useImageMutation';

import { IMAGE_COMPRESSION_OPTIONS } from '@constants/image';

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
      const originalImageFile = event.target.files?.[0];

      if (!originalImageFile) return;

      let imageFile: File;

      try {
        imageFile = await imageCompression(originalImageFile, IMAGE_COMPRESSION_OPTIONS);
      } catch (e) {
        imageFile = originalImageFile;
      }

      const imageUploadFormData = new FormData();
      imageUploadFormData.append('images', imageFile);

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
