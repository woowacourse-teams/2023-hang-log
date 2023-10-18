import { useCallback } from 'react';

import { useRecoilValue } from 'recoil';

import { Button, Flex, ImageUploadInput, Modal, Theme } from 'hang-log-design-system';

import GoogleMapWrapper from '@components/common/GoogleMapWrapper/GoogleMapWrapper';
import CategoryInput from '@components/trip/TripItemAddModal/CategoryInput/CategoryInput';
import DateInput from '@components/trip/TripItemAddModal/DateInput/DateInput';
import ExpenseInput from '@components/trip/TripItemAddModal/ExpenseInput/ExpenseInput';
import MemoInput from '@components/trip/TripItemAddModal/MemoInput/MemoInput';
import PlaceInput from '@components/trip/TripItemAddModal/PlaceInput/PlaceInput';
import StarRatingInput from '@components/trip/TripItemAddModal/StarRatingInput/StarRatingInput';
import TitleInput from '@components/trip/TripItemAddModal/TitleInput/TitleInput';
import {
  buttonStyling,
  formStyling,
  wrapperStyling,
} from '@components/trip/TripItemAddModal/TripItemAddModal.style';

import { useMultipleImageUpload } from '@hooks/common/useMultipleImageUpload';
import { useToast } from '@hooks/common/useToast';
import { useAddTripItemForm } from '@hooks/trip/useAddTripItemForm';

import { mediaQueryMobileState } from '@store/mediaQuery';

import type { TripTypeData } from '@type/trip';
import type { TripItemFormData } from '@type/tripItem';

import { TRIP_TYPE } from '@constants/trip';
import { TRIP_ITEM_ADD_MAX_IMAGE_UPLOAD_COUNT } from '@constants/ui';

interface TripItemAddModalProps {
  tripId: string;
  dayLogId: number;
  itemId?: number;
  initialData?: TripItemFormData;
  isOpen?: boolean;
  onClose: () => void;
}

const TripItemAddModal = ({
  tripId,
  dayLogId,
  itemId,
  initialData,
  isOpen = true,
  onClose,
}: TripItemAddModalProps) => {
  const isMobile = useRecoilValue(mediaQueryMobileState);
  const { createToast } = useToast();

  const { tripItemInformation, isTitleError, updateInputValue, disableTitleError, handleSubmit } =
    useAddTripItemForm({
      tripType: TRIP_TYPE.PERSONAL as TripTypeData,
      tripId,
      initialDayLogId: dayLogId,
      itemId,
      initialData,
      onSuccess: onClose,
    });

  const handleImageNamesChange = useCallback(
    (imageNames: string[]) => {
      updateInputValue('imageNames', imageNames);
    },
    [updateInputValue]
  );

  const handleImageUploadError = () => {
    createToast('이미지는 최대 5개 업로드할 수 있습니다.');
  };

  const { isImageUploading, imageUrls, handleImageUpload, handleImageRemoval } =
    useMultipleImageUpload({
      initialImageNames: tripItemInformation.imageNames,
      updateFormImage: handleImageNamesChange,
      onError: handleImageUploadError,
    });

  return (
    <Modal
      css={wrapperStyling}
      isOpen={isOpen}
      closeModal={onClose}
      isBackdropClosable={false}
      hasCloseButton
    >
      <GoogleMapWrapper>
        <form css={formStyling} onSubmit={handleSubmit} noValidate>
          <Flex
            styles={{
              gap: Theme.spacer.spacing4,
              direction: isMobile ? 'column' : 'row',
              marginBottom: Theme.spacer.spacing4,
            }}
          >
            <Flex
              styles={{
                direction: 'column',
                gap: Theme.spacer.spacing3,
                width: isMobile ? '100%' : '312px',
                align: 'stretch',
              }}
            >
              <CategoryInput
                itemType={tripItemInformation.itemType}
                updateInputValue={updateInputValue}
                disableError={disableTitleError}
              />
              <DateInput
                currentCategory={tripItemInformation.itemType}
                dayLogId={dayLogId}
                tripId={tripId}
                updateInputValue={updateInputValue}
              />
              {tripItemInformation.itemType ? (
                <PlaceInput
                  value={tripItemInformation.title}
                  isError={isTitleError}
                  isUpdatable={tripItemInformation.isPlaceUpdated !== undefined}
                  updateInputValue={updateInputValue}
                  disableError={disableTitleError}
                />
              ) : (
                <TitleInput
                  value={tripItemInformation.title}
                  isError={isTitleError}
                  updateInputValue={updateInputValue}
                  disableError={disableTitleError}
                />
              )}
              <StarRatingInput
                rating={tripItemInformation.rating}
                updateInputValue={updateInputValue}
                isMobile={isMobile}
              />
              <ExpenseInput
                initialExpenseValue={tripItemInformation.expense}
                updateInputValue={updateInputValue}
              />
            </Flex>
            <Flex
              styles={{
                direction: 'column',
                gap: '16px',
                width: isMobile ? '100%' : '312px',
                align: 'stretch',
              }}
            >
              <MemoInput value={tripItemInformation.memo} updateInputValue={updateInputValue} />
              <ImageUploadInput
                id="image-upload"
                label="이미지 업로드"
                imageUrls={imageUrls}
                imageAltText="여행 일정 업로드 이미지"
                supportingText="사진은 최대 5장 올릴 수 있어요."
                maxUploadCount={TRIP_ITEM_ADD_MAX_IMAGE_UPLOAD_COUNT}
                multiple
                onChange={handleImageUpload}
                onRemove={handleImageRemoval}
              />
            </Flex>
          </Flex>
          <Button
            variant={isImageUploading ? 'default' : 'primary'}
            disabled={isImageUploading}
            css={buttonStyling}
          >
            일정 기록 {itemId ? '수정하기' : '추가하기'}
          </Button>
        </form>
      </GoogleMapWrapper>
    </Modal>
  );
};

export default TripItemAddModal;
