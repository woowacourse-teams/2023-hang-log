import type { ChangeEvent } from 'react';
import { useCallback, useState } from 'react';

import imageCompression from 'browser-image-compression';

import { useImageMutation } from '@hooks/api/useImageMutation';
import { useToast } from '@hooks/common/useToast';

import { IMAGE_COMPRESSION_OPTIONS } from '@constants/image';

interface UseSingleImageUploadParams {
  initialImageName: string | null;
  onSuccess?: CallableFunction;
}

export const useSingleImageUpload = ({
  initialImageName,
  onSuccess,
}: UseSingleImageUploadParams) => {
  const imageMutation = useImageMutation();
  const isImageUploading = imageMutation.isLoading;

  const { createToast } = useToast();

  const [uploadedImageName, setUploadedImageName] = useState(initialImageName);

  const handleImageUpload = useCallback(
    async (event: ChangeEvent<HTMLInputElement>) => {
      const originalImageFile = event.target.files?.[0];

      if (!originalImageFile) return;

      const prevImageName = uploadedImageName;

      setUploadedImageName(URL.createObjectURL(originalImageFile));

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
          onSuccess: ({ imageNames }) => {
            onSuccess?.(imageNames[0]);
            createToast('이미지 업로드에 성공했습니다', 'success');
          },
          onError: () => {
            setUploadedImageName(prevImageName);
          },
        }
      );

      // eslint-disable-next-line no-param-reassign
      event.target.value = '';
    },
    [createToast, imageMutation, onSuccess, uploadedImageName]
  );

  const handleImageRemoval = useCallback(() => {
    setUploadedImageName(null);
    onSuccess?.(null);
  }, [onSuccess]);

  return { isImageUploading, uploadedImageName, handleImageUpload, handleImageRemoval };
};
