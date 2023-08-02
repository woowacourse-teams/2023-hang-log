import { ImageUploadInput } from 'hang-log-design-system';
import { useCallback } from 'react';

import { useImageUpload } from '@hooks/common/useImageUpload';

interface ImageInputProps {
  initialImage: string | null;
  updateCoverImage: (imageUrl: string) => void;
}

const ImageInput = ({ initialImage, updateCoverImage }: ImageInputProps) => {
  const handleImageUrlsChange = useCallback(
    (imageUrls: string[]) => {
      updateCoverImage(imageUrls[0]);
    },
    [updateCoverImage]
  );

  const handleImageUploadError = (errorMessage: string) => {
    console.log(errorMessage);
  };

  const {
    uploadedImageUrls: uploadedImageUrl,
    handleImageUpload,
    handleImageRemoval,
  } = useImageUpload({
    initialImageUrls: initialImage === null ? [] : [initialImage],
    onSuccess: handleImageUrlsChange,
    onError: handleImageUploadError,
  });

  return (
    <ImageUploadInput
      id="cover-image-upload"
      label="대표 이미지 업로드"
      imageAltText="여행 대표 업로드 이미지"
      imageUrls={uploadedImageUrl}
      maxUploadCount={1}
      onChange={handleImageUpload}
      onRemove={handleImageRemoval}
    />
  );
};

export default ImageInput;
