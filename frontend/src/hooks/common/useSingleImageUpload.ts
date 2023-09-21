import type { ChangeEvent } from 'react';
import { useCallback, useState } from 'react';

import imageCompression from 'browser-image-compression';

import { useImageMutation } from '@hooks/api/useImageMutation';
import { useToast } from '@hooks/common/useToast';

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

  const { createToast } = useToast();

  const [uploadedImageUrl, setUploadedImageUrl] = useState(initialImageUrl);

  const handleImageUpload = useCallback(
    async (event: ChangeEvent<HTMLInputElement>) => {
      const originalImageFile = event.target.files?.[0];

      if (!originalImageFile) return;

      const prevImageUrl = uploadedImageUrl;

      setUploadedImageUrl(URL.createObjectURL(originalImageFile));

      let imageFile: File;

      try {
        const compressedImageFile = await imageCompression(
          originalImageFile,

          IMAGE_COMPRESSION_OPTIONS
        );

        const fileName = originalImageFile.name;

        const fileType = compressedImageFile.type;

        imageFile = new File([compressedImageFile], fileName, { type: fileType });
      } catch (e) {
        imageFile = originalImageFile;
      }

      const imageUploadFormData = new FormData();
      imageUploadFormData.append('images', imageFile);

      imageMutation.mutate(
        { images: imageUploadFormData },
        {
          onSuccess: ({ imageUrls }) => {
            onSuccess?.(imageUrls[0]);
            createToast('이미지 업로드에 성공했습니다', 'success');
          },
          onError: () => {
            setUploadedImageUrl(prevImageUrl);
          },
        }
      );

      // eslint-disable-next-line no-param-reassign
      event.target.value = '';
    },
    [createToast, imageMutation, onSuccess, uploadedImageUrl]
  );

  const handleImageRemoval = useCallback(() => {
    setUploadedImageUrl(null);
    onSuccess?.(null);
  }, [onSuccess]);

  return { uploadedImageUrl, handleImageUpload, handleImageRemoval };
};
