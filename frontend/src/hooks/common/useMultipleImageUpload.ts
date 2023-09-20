import type { ChangeEvent } from 'react';
import { useCallback, useState } from 'react';

import imageCompression from 'browser-image-compression';

import { useImageMutation } from '@hooks/api/useImageMutation';
import { useToast } from '@hooks/common/useToast';

import { IMAGE_COMPRESSION_OPTIONS } from '@constants/image';
import { TRIP_ITEM_ADD_MAX_IMAGE_UPLOAD_COUNT } from '@constants/ui';

interface UseMultipleImageUploadParams {
  initialImageUrls: string[];
  maxUploadCount?: number;
  onSuccess?: CallableFunction;
  onError?: CallableFunction;
}

export const useMultipleImageUpload = ({
  initialImageUrls,
  maxUploadCount = TRIP_ITEM_ADD_MAX_IMAGE_UPLOAD_COUNT,
  onSuccess,
  onError,
}: UseMultipleImageUploadParams) => {
  const imageMutation = useImageMutation();

  const { createToast } = useToast();

  const [uploadedImageUrls, setUploadedImageUrls] = useState(initialImageUrls);

  const handleImageUpload = useCallback(
    async (event: ChangeEvent<HTMLInputElement>) => {
      const originalImageFiles = event.target.files;

      if (!originalImageFiles) return;

      if (originalImageFiles.length + uploadedImageUrls.length > maxUploadCount) {
        onError?.();

        return;
      }

      const prevImageUrls = uploadedImageUrls;

      setUploadedImageUrls((prevImageUrls) => {
        const newImageUrls = [...originalImageFiles].map((file) => URL.createObjectURL(file));

        return [...prevImageUrls, ...newImageUrls];
      });

      const imageFiles: File[] = [];

      try {
        await Promise.all(
          [...originalImageFiles].map(async (file) => {
            const compressedImageFile = await imageCompression(file, IMAGE_COMPRESSION_OPTIONS);
            imageFiles.push(compressedImageFile);
          })
        );
      } catch (e) {
        imageFiles.push(...originalImageFiles);
      }

      const imageUploadFormData = new FormData();

      [...imageFiles].forEach((file) => {
        imageUploadFormData.append('images', file);
      });

      imageMutation.mutate(
        { images: imageUploadFormData },
        {
          onSuccess: ({ imageUrls }) => {
            onSuccess?.([...prevImageUrls, ...imageUrls]);
            createToast('이미지 업로드에 성공했습니다', 'success');
          },
          onError: () => {
            setUploadedImageUrls(prevImageUrls);
          },
        }
      );

      // eslint-disable-next-line no-param-reassign
      event.target.value = '';
    },
    [createToast, imageMutation, maxUploadCount, onError, onSuccess, uploadedImageUrls]
  );

  const handleImageRemoval = useCallback(
    (selectedImageUrl: string) => () => {
      setUploadedImageUrls((prevImageUrls) => {
        const updatedImageUrls = prevImageUrls.filter((imageUrl) => imageUrl !== selectedImageUrl);
        onSuccess?.(updatedImageUrls);

        return updatedImageUrls;
      });
    },
    [onSuccess]
  );

  return { uploadedImageUrls, handleImageUpload, handleImageRemoval };
};
