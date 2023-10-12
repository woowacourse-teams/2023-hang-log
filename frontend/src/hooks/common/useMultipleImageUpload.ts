import type { ChangeEvent } from 'react';
import { useCallback, useState } from 'react';

import imageCompression from 'browser-image-compression';

import { useImageMutation } from '@hooks/api/useImageMutation';
import { useToast } from '@hooks/common/useToast';

import convertImageNames from '@utils/convertImageNames';

import { IMAGE_COMPRESSION_OPTIONS } from '@constants/image';
import { TRIP_ITEM_ADD_MAX_IMAGE_UPLOAD_COUNT } from '@constants/ui';

interface UseMultipleImageUploadParams {
  initialImageNames: string[];
  maxUploadCount?: number;
  handleInitialImage?: (images: string[]) => void;
  onSuccess?: CallableFunction;
  onError?: CallableFunction;
}

export const useMultipleImageUpload = ({
  initialImageNames,
  maxUploadCount = TRIP_ITEM_ADD_MAX_IMAGE_UPLOAD_COUNT,
  onSuccess,
  onError,
}: UseMultipleImageUploadParams) => {
  const imageMutation = useImageMutation();
  const isImageUploading = imageMutation.isLoading;

  const convertedImageNames = convertImageNames(initialImageNames);

  const { createToast } = useToast();
  const [uploadedImageNames, setUploadedImageNames] = useState(convertedImageNames);

  const handleImageUpload = useCallback(
    async (event: ChangeEvent<HTMLInputElement>) => {
      const originalImageFiles = event.target.files;

      if (!originalImageFiles) return;

      if (originalImageFiles.length + uploadedImageNames.length > maxUploadCount) {
        onError?.();

        return;
      }

      const prevImageNames = uploadedImageNames;

      setUploadedImageNames((prevImageNames) => {
        const newImageNames = [...originalImageFiles].map((file) => URL.createObjectURL(file));

        return [...prevImageNames, ...newImageNames];
      });

      const imageFiles: File[] = [];

      try {
        await Promise.all(
          [...originalImageFiles].map(async (file) => {
            const compressedImageFile = await imageCompression(file, IMAGE_COMPRESSION_OPTIONS);

            const fileName = file.name;
            const fileType = compressedImageFile.type;
            const convertedFile = new File([compressedImageFile], fileName, { type: fileType });

            imageFiles.push(convertedFile);
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
          onSuccess: ({ imageNames }) => {
            if (maxUploadCount === 1) {
              onSuccess?.([...imageNames]);
              createToast('이미지 업로드에 성공했습니다', 'success');

              return;
            }

            onSuccess?.([...convertedImageNames, ...imageNames]);
            createToast('이미지 업로드에 성공했습니다', 'success');
          },
          onError: () => {
            setUploadedImageNames(prevImageNames);
          },
        }
      );

      // eslint-disable-next-line no-param-reassign
      event.target.value = '';
    },
    [
      createToast,
      imageMutation,
      convertedImageNames,
      maxUploadCount,
      onError,
      onSuccess,
      uploadedImageNames,
    ]
  );

  const handleImageRemoval = useCallback(
    (selectedImageName: string) => () => {
      setUploadedImageNames((prevImageNames) => {
        const updatedImageNames = prevImageNames.filter(
          (imageName) => imageName !== selectedImageName
        );

        onSuccess?.(updatedImageNames);

        return updatedImageNames;
      });
    },
    [onSuccess]
  );

  return { isImageUploading, uploadedImageNames, handleImageUpload, handleImageRemoval };
};
