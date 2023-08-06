import { TRIP_ITEM_ADD_MAX_IMAGE_UPLOAD_COUNT } from '@constants/ui';
import { toastListState } from '@store/toast';
import type { TripItemFormData } from '@type/tripItem';
import { ImageUploadInput } from 'hang-log-design-system';
import { useCallback } from 'react';
import { useSetRecoilState } from 'recoil';

import { generateUniqueId } from '@utils/uniqueId';

import { useImageUpload } from '@hooks/common/useImageUpload';

interface ImageInputProps {
  initialImageUrls: string[];
  updateInputValue: <K extends keyof TripItemFormData>(key: K, value: TripItemFormData[K]) => void;
}

const ImageInput = ({ initialImageUrls, updateInputValue }: ImageInputProps) => {
  const setToastList = useSetRecoilState(toastListState);

  const handleImageUrlsChange = useCallback(
    (imageUrls: string[]) => {
      updateInputValue('imageUrls', imageUrls);
    },
    [updateInputValue]
  );

  const handleImageUploadError = () => {
    setToastList((prevToastList) => [
      ...prevToastList,
      {
        id: generateUniqueId(),
        variant: 'error',
        message: '이미지는 최대 5개 업로드할 수 있습니다.',
      },
    ]);
  };

  const { uploadedImageUrls, handleImageUpload, handleImageRemoval } = useImageUpload({
    initialImageUrls,
    onSuccess: handleImageUrlsChange,
    onError: handleImageUploadError,
  });

  return (
    <ImageUploadInput
      id="image-upload"
      label="이미지 업로드"
      imageUrls={uploadedImageUrls}
      imageAltText="여행 일정 업로드 이미지"
      supportingText="사진은 최대 5장 올릴 수 있어요."
      maxUploadCount={TRIP_ITEM_ADD_MAX_IMAGE_UPLOAD_COUNT}
      multiple
      onChange={handleImageUpload}
      onRemove={handleImageRemoval}
    />
  );
};

export default ImageInput;
