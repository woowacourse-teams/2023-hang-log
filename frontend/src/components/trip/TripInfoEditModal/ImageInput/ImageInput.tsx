import { useSingleImageUpload } from '@/hooks/common/useSingleImageUpload';

import { useCallback } from 'react';

import { ImageUploadInput } from 'hang-log-design-system';

interface ImageInputProps {
  initialImage: string | null;
  updateCoverImage: (imageUrl: string) => void;
}

const ImageInput = ({ initialImage, updateCoverImage }: ImageInputProps) => {
  const handleImageUrlsChange = useCallback(
    (imageUrl: string) => {
      updateCoverImage(imageUrl);
    },
    [updateCoverImage]
  );

  const { uploadedImageUrl, handleImageUpload, handleImageRemoval } = useSingleImageUpload({
    initialImageUrl: initialImage,
    onSuccess: handleImageUrlsChange,
  });

  return (
    <ImageUploadInput
      id="cover-image-upload"
      label="대표 이미지 업로드"
      imageAltText="여행 대표 업로드 이미지"
      imageUrls={uploadedImageUrl ? [uploadedImageUrl] : null}
      maxUploadCount={1}
      onChange={handleImageUpload}
      onRemove={handleImageRemoval}
    />
  );
};

export default ImageInput;
