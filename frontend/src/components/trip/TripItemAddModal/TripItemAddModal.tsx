import { TRIP_ITEM_ADD_MAX_IMAGE_UPLOAD_COUNT } from '@constants/ui';
import { Button, Flex, ImageUploadInput, Modal, Theme } from 'hang-log-design-system';

import { useAddTripItemForm } from '@hooks/trip/useAddTripItemForm';

import GoogleMapWrapper from '@components/common/GoogleMapWrapper/GoogleMapWrapper';
import CategoryInput from '@components/trip/TripItemAddModal/CategoryInput/CategoryInput';
import DateInput from '@components/trip/TripItemAddModal/DateInput/DateInput';
import ExpenseInput from '@components/trip/TripItemAddModal/ExpenseInput/ExpenseInput';
import MemoInput from '@components/trip/TripItemAddModal/MemoInput/MemoInput';
import PlaceInput from '@components/trip/TripItemAddModal/PlaceInput/PlaceInput';
import StarRatingInput from '@components/trip/TripItemAddModal/StarRatingInput/StarRatingInput';
import TitleInput from '@components/trip/TripItemAddModal/TitleInput/TitleInput';
import {
  formStyling,
  wrapperStyling,
} from '@components/trip/TripItemAddModal/TripItemAddModal.style';

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
              <CategoryInput
                itemType={tripItemInformation.itemType}
                updateInputValue={updateInputValue}
                disableError={disableTitleError}
              />
              <DateInput
                currentCategory={tripItemInformation.itemType}
                dayLogId={dayLogId}
                dates={dates}
                updateInputValue={updateInputValue}
              />
              {tripItemInformation.itemType ? (
                <PlaceInput
                  value={tripItemInformation.title}
                  isError={isTitleError}
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
              />
              <ExpenseInput
                initialExpenseValue={tripItemInformation.expense}
                updateInputValue={updateInputValue}
              />
            </Flex>
            <Flex styles={{ direction: 'column', gap: '16px', width: '312px', align: 'stretch' }}>
              <MemoInput value={tripItemInformation.memo} updateInputValue={updateInputValue} />
              {/* TODO : 이미지 업로드 관련 로직 처리 필요함 */}
              <ImageUploadInput
                label="이미지 업로드"
                imageUrls={tripItemInformation.imageUrls}
                imageAltText="여행 일정 업르드 이미지"
                supportingText="사진은 최대 5장 올릴 수 있어요."
                maxUploadCount={TRIP_ITEM_ADD_MAX_IMAGE_UPLOAD_COUNT}
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
