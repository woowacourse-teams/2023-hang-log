import { useCallback } from 'react';

import { ImageUploadInput } from 'hang-log-design-system';

import { useMultipleImageUpload } from '@hooks/common/useMultipleImageUpload';

interface ImageInputProps {
  initialImage: string | null;
  updateCoverImage: (imageName: string) => void;
}

const ImageInput = ({ initialImage, updateCoverImage }: ImageInputProps) => {
  const handleImageNamesChange = useCallback(
    (imageNames: string[]) => {
      updateCoverImage(imageNames[0]);
    },
    [updateCoverImage]
  );

  const {
    uploadedImageNames: uploadedImageName,
    handleImageUpload,
    handleImageRemoval,
  } = useMultipleImageUpload({
    initialImageNames: initialImage === null ? [] : [initialImage],
    onSuccess: handleImageNamesChange,
  });

  return (
    <ImageUploadInput
      id="cover-image-upload"
      label="대표 이미지 업로드"
      imageAltText="여행 대표 업로드 이미지"
      imageUrls={uploadedImageName}
      maxUploadCount={1}
      onChange={handleImageUpload}
      onRemove={handleImageRemoval}
    />
  );
};

export default ImageInput;
