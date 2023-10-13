import { convertToImageNames, convertToImageUrls } from '@/utils/convertImage';

import type { ChangeEvent } from 'react';
import { useCallback, useState } from 'react';

import imageCompression from 'browser-image-compression';

import { useImageMutation } from '@hooks/api/useImageMutation';

import { IMAGE_COMPRESSION_OPTIONS } from '@constants/image';
import { TRIP_ITEM_ADD_MAX_IMAGE_UPLOAD_COUNT } from '@constants/ui';

interface UseMultipleImageUploadParams {
  initialImageNames: string[];
  maxUploadCount?: number;
  updateFormImage?: CallableFunction;
  onError?: CallableFunction;
}

export const useMultipleImageUpload = ({
  initialImageNames,
  maxUploadCount = TRIP_ITEM_ADD_MAX_IMAGE_UPLOAD_COUNT,
  updateFormImage,
  onError,
}: UseMultipleImageUploadParams) => {
  const imageMutation = useImageMutation();
  const isImageUploading = imageMutation.isLoading;

  const initialImageUrls = convertToImageUrls(initialImageNames);

  const [uploadedImageUrls, setUploadedImageUrls] = useState(initialImageUrls);

  const compressImages = useCallback(async (originalImageFiles: FileList): Promise<File[]> => {
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

    return imageFiles;
  }, []);

  const convertToImageFormData = useCallback(
    async (imageFiles: FileList) => {
      const compressedImages = await compressImages(imageFiles);
      const imageFormData = new FormData();

      compressedImages.forEach((file) => {
        imageFormData.append('images', file);
      });

      return imageFormData;
    },
    [compressImages]
  );

  const postImageNames = useCallback(
    async (images: FormData) => {
      imageMutation.mutate(
        { images },
        {
          onSuccess: ({ imageNames }) => {
            if (maxUploadCount === 1) {
              updateFormImage?.([...imageNames]);

              return;
            }

            updateFormImage?.([...initialImageNames, ...imageNames]);
          },
          onError: () => {
            setUploadedImageUrls(initialImageUrls);
          },
        }
      );
    },
    [imageMutation, maxUploadCount, updateFormImage, initialImageNames, initialImageUrls]
  );

  const handleImageUpload = useCallback(
    async (event: ChangeEvent<HTMLInputElement>) => {
      const originalImageFiles = event.target.files;

      if (!originalImageFiles) return;

      if (originalImageFiles.length + uploadedImageUrls.length > maxUploadCount) {
        onError?.();

        return;
      }

      setUploadedImageUrls((prevImageUrls) => {
        const newImageUrls = [...originalImageFiles].map((file) => URL.createObjectURL(file));

        return [...prevImageUrls, ...newImageUrls];
      });
      console.log(originalImageFiles);
      const imageFormData = await convertToImageFormData(originalImageFiles);
      console.log(imageFormData);
      postImageNames(imageFormData);

      // eslint-disable-next-line no-param-reassign
      event.target.value = '';
    },
    [uploadedImageUrls, maxUploadCount, convertToImageFormData, postImageNames, onError]
  );

  const handleImageRemoval = useCallback(
    (selectedImageUrl: string) => () => {
      setUploadedImageUrls((prevImageUrls) => {
        const updatedImageUrls = prevImageUrls.filter((imageUrl) => imageUrl !== selectedImageUrl);

        const imageNames = convertToImageNames(updatedImageUrls);
        updateFormImage?.(imageNames);

        return updatedImageUrls;
      });
    },
    [updateFormImage]
  );

  return { isImageUploading, uploadedImageUrls, handleImageUpload, handleImageRemoval };
};
