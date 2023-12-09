import type { ChangeEvent } from 'react';
import { useCallback, useMemo, useState } from 'react';

import imageCompression from 'browser-image-compression';

import { useImageMutation } from '@hooks/api/useImageMutation';

import { convertToImageNames, convertToImageUrls } from '@utils/convertImage';

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
  const isImageUploading = imageMutation.isPending;

  const initialImageUrls = convertToImageUrls([...initialImageNames]);

  const [imageUrls, setImageUrls] = useState(initialImageUrls);
  const uploadedImageNames = useMemo(() => [...initialImageNames], [initialImageNames]);

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

            uploadedImageNames.push(...imageNames);

            updateFormImage?.(uploadedImageNames);
          },
          onError: () => {
            setImageUrls(initialImageUrls);
          },
        }
      );
    },
    [imageMutation, maxUploadCount, uploadedImageNames, updateFormImage, initialImageUrls]
  );

  const handleImageUpload = useCallback(
    async (event: ChangeEvent<HTMLInputElement>) => {
      const originalImageFiles = event.target.files;

      if (!originalImageFiles) return;

      if (originalImageFiles.length + imageUrls.length > maxUploadCount) {
        onError?.();

        return;
      }

      // 화면에 보여지는 이미지 url로 변경 + 업데이트
      setImageUrls((prevImageUrls) => {
        const newImageUrls = [...originalImageFiles].map((file) => URL.createObjectURL(file));

        return [...prevImageUrls, ...newImageUrls];
      });

      const imageFormData = await convertToImageFormData(originalImageFiles);
      postImageNames(imageFormData);

      // eslint-disable-next-line no-param-reassign
      event.target.value = '';
    },
    [imageUrls, maxUploadCount, convertToImageFormData, postImageNames, onError]
  );

  const handleImageRemoval = useCallback(
    (selectedImageUrl: string) => () => {
      setImageUrls((prevImageUrls) => {
        const updatedImageUrls = prevImageUrls.filter((imageUrl) => imageUrl !== selectedImageUrl);

        const imageNames = convertToImageNames(updatedImageUrls);
        updateFormImage?.(imageNames);

        return updatedImageUrls;
      });
    },
    [updateFormImage]
  );

  return { isImageUploading, imageUrls, handleImageUpload, handleImageRemoval };
};
