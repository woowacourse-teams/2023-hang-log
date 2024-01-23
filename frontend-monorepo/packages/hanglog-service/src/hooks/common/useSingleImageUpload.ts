import type { ChangeEvent } from 'react';
import { useCallback, useState } from 'react';

import imageCompression from 'browser-image-compression';

import { useImageMutation } from '@hooks/api/useImageMutation';

import { convertToImageUrl } from '@utils/convertImage';

import { IMAGE_COMPRESSION_OPTIONS } from '@constants/image';

interface UseSingleImageUploadParams {
  initialImageUrl: string | null;
  updateFormImage?: CallableFunction;
}

export const useSingleImageUpload = ({
  initialImageUrl,
  updateFormImage,
}: UseSingleImageUploadParams) => {
  const imageMutation = useImageMutation();
  const isImageUploading = imageMutation.isPending;

  const [uploadedImageUrl, setUploadedImageUrl] = useState(initialImageUrl);

  const compressImage = useCallback(async (originalImageFile: File) => {
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

    return imageFile;
  }, []);

  const convertToImageFormData = useCallback(
    async (imageFile: File) => {
      const compressedImage = await compressImage(imageFile);

      const imageUploadFormData = new FormData();
      imageUploadFormData.append('images', compressedImage);

      return imageUploadFormData;
    },
    [compressImage]
  );

  const handleImageUpload = useCallback(
    async (event: ChangeEvent<HTMLInputElement>) => {
      const originalImageFile = event.target.files?.[0];

      if (!originalImageFile) return;

      const prevImageName = uploadedImageUrl;

      setUploadedImageUrl(URL.createObjectURL(originalImageFile));

      const images = await convertToImageFormData(originalImageFile);

      imageMutation.mutate(
        { images },
        {
          onSuccess: ({ imageNames }) => {
            const imageUrl = convertToImageUrl(imageNames[0]);
            updateFormImage?.(imageUrl);
          },
          onError: () => {
            setUploadedImageUrl(prevImageName);
          },
        }
      );

      // eslint-disable-next-line no-param-reassign
      event.target.value = '';
    },
    [convertToImageFormData, imageMutation, updateFormImage, uploadedImageUrl]
  );

  const handleImageRemoval = useCallback(() => {
    setUploadedImageUrl(null);
    updateFormImage?.(null);
  }, [updateFormImage]);

  return { isImageUploading, uploadedImageUrl, handleImageUpload, handleImageRemoval };
};
