import { TRIP_ITEM_ADD_MAX_IMAGE_UPLOAD_COUNT } from '@constants/ui';
import type { TripItemFormData } from '@type/tripItem';
import { ImageUploadInput, Toast, useOverlay } from 'hang-log-design-system';
import { useCallback, useState } from 'react';

import { useImageUpload } from '@hooks/common/useImageUpload';

interface ImageInputProps {
  initialImageUrls: string[];
  updateInputValue: <K extends keyof TripItemFormData>(key: K, value: TripItemFormData[K]) => void;
}

const ImageInput = ({ initialImageUrls, updateInputValue }: ImageInputProps) => {
  const [errorMessage, setErrorMessage] = useState('');
  const { isOpen: isErrorTostOpen, open: openErrorToast, close: closeErrorToast } = useOverlay();

  const handleImageUrlsChange = useCallback(
    (imageUrls: string[]) => {
      updateInputValue('imageUrls', imageUrls);
    },
    [updateInputValue]
  );

  const handleImageUploadError = (errorMessage: string) => {
    setErrorMessage(errorMessage);
    openErrorToast();
  };

  const { uploadedImageUrls, handleImageUpload, handleImageRemoval } = useImageUpload({
    initialImageUrls,
    onSuccess: handleImageUrlsChange,
    onError: handleImageUploadError,
  });

  return (
    <>
      <ImageUploadInput
        id="image-upload"
        label="이미지 업로드"
        imageUrls={uploadedImageUrls}
        imageAltText="여행 일정 업르드 이미지"
        supportingText="사진은 최대 5장 올릴 수 있어요."
        maxUploadCount={TRIP_ITEM_ADD_MAX_IMAGE_UPLOAD_COUNT}
        multiple
        onChange={handleImageUpload}
        onRemove={handleImageRemoval}
      />
      {isErrorTostOpen && (
        <Toast variant="error" closeToast={closeErrorToast}>
          {errorMessage}
        </Toast>
      )}
    </>
  );
};

export default ImageInput;
