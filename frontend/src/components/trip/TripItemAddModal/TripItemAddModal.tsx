import { Button, Flex, ImageUploadInput, Modal, Theme } from 'hang-log-design-system';

import { useAddTripItemForm } from '@hooks/trip/useAddTripItemForm';

import GoogleMapWrapper from '@components/common/GoogleMapWrapper/GoogleMapWrapper';
import {
  formStyling,
  wrapperStyling,
} from '@components/trip/TripItemAddModal/TripItemAddModal.style';
import TripItemCategoryInput from '@components/trip/TripItemAddModal/TripItemCategoryInput/TripItemCategoryInput';
import TripItemDateInput from '@components/trip/TripItemAddModal/TripItemDateInput/TripItemDateInput';
import TripItemExpenseInput from '@components/trip/TripItemAddModal/TripItemExpenseInput/TripItemExpenseInput';
import TripItemMemoInput from '@components/trip/TripItemAddModal/TripItemMemoInput/TripItemMemoInput';
import TripItemPlaceInput from '@components/trip/TripItemAddModal/TripItemPlaceInput/TripItemPlaceInput';
import TripItemStarRatingInput from '@components/trip/TripItemAddModal/TripItemStarRatingInput/TripItemStarRatingInput';
import TripItemTitleInput from '@components/trip/TripItemAddModal/TripItemTitleInput/TripItemTitleInput';

interface TripItemAddModalProps {
  isOpen: boolean;
  tripId: number;
  dayLogId: number;
  dates: { id: number; date: string }[];
  onClose: () => void;
}

const TripItemAddModal = ({ isOpen, tripId, dates, dayLogId, onClose }: TripItemAddModalProps) => {
  const { tripItemInformation, isTitleError, updateInputValue, disableTitleError, handleSubmit } =
    useAddTripItemForm(tripId, dayLogId, onClose);

  return (
    <Modal css={wrapperStyling} isOpen={isOpen} closeModal={onClose} hasCloseButton>
      <GoogleMapWrapper>
        <form css={formStyling} onSubmit={handleSubmit} noValidate>
          <Flex styles={{ gap: Theme.spacer.spacing4 }}>
            <Flex styles={{ direction: 'column', gap: '16px', width: '312px', align: 'stretch' }}>
              <TripItemCategoryInput
                itemType={tripItemInformation.itemType}
                updateInputValue={updateInputValue}
                disableError={disableTitleError}
              />
              <TripItemDateInput
                currentCategory={tripItemInformation.itemType}
                dayLogId={dayLogId}
                dates={dates}
                updateInputValue={updateInputValue}
              />
              {tripItemInformation.itemType ? (
                <TripItemPlaceInput
                  value={tripItemInformation.title}
                  isError={isTitleError}
                  updateInputValue={updateInputValue}
                  disableError={disableTitleError}
                />
              ) : (
                <TripItemTitleInput
                  value={tripItemInformation.title}
                  isError={isTitleError}
                  updateInputValue={updateInputValue}
                  disableError={disableTitleError}
                />
              )}
              <TripItemStarRatingInput
                rating={tripItemInformation.rating}
                updateInputValue={updateInputValue}
              />
              <TripItemExpenseInput
                initialExpenseValue={tripItemInformation.expense}
                updateInputValue={updateInputValue}
              />
            </Flex>
            <Flex styles={{ direction: 'column', gap: '16px', width: '312px', align: 'stretch' }}>
              <TripItemMemoInput
                value={tripItemInformation.memo}
                updateInputValue={updateInputValue}
              />
              {/* TODO : 이미지 업로드 관련 로직 처리 필요함 */}
              <ImageUploadInput
                label="이미지 업로드"
                imageUrls={tripItemInformation.imageUrls}
                imageAltText="여행 일정 업르드 이미지"
                supportingText="사진은 최대 5장 올릴 수 있어요."
                maxUploadCount={5}
                onRemove={() => {}}
              />
            </Flex>
          </Flex>
          <Button variant="primary">일정 기록 추가하기</Button>
        </form>
      </GoogleMapWrapper>
    </Modal>
  );
};

export default TripItemAddModal;
