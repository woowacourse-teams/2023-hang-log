import { Button, Flex, ImageUploadInput, Input, Modal, Theme } from 'hang-log-design-system';

import { useAddTripItemForm } from '@hooks/useAddTripItemForm';

import { formStyling } from '@components/trip/TripItemAddModal/TripItemAddModal.style';
import TripItemCategoryInput from '@components/trip/TripItemAddModal/TripItemCategoryInput/TripItemCategoryInput';
import TripItemDateInput from '@components/trip/TripItemAddModal/TripItemDateInput/TripItemDateInput';
import TripItemExpenseInput from '@components/trip/TripItemAddModal/TripItemExpenseInput/TripItemExpenseInput';
import TripItemMemoInput from '@components/trip/TripItemAddModal/TripItemMemoInput/TripItemMemoInput';
import TripItemStarRatingInput from '@components/trip/TripItemAddModal/TripItemStarRatingInput/TripItemStarRatingInput';
import TripItemTitleInput from '@components/trip/TripItemAddModal/TripItemTitleInput/TripItemTitleInput';

interface TripItemAddModalProps {
  isOpen: boolean;
  tripId: number;
  currentDate: { id: number; date: string };
  dates: { id: number; date: string }[];
  onClose: () => void;
}

const TripItemAddModal = ({
  isOpen,
  tripId,
  dates,
  currentDate,
  onClose,
}: TripItemAddModalProps) => {
  const { tripItemInformation, isTitleError, updateInputValue, disableTitleError, handleSubmit } =
    useAddTripItemForm(tripId, currentDate.id, onClose);

  return (
    <Modal isOpen={isOpen} closeModal={onClose} hasCloseButton>
      <form css={formStyling} onSubmit={handleSubmit} noValidate>
        <Flex styles={{ gap: Theme.spacer.spacing4 }}>
          <Flex styles={{ direction: 'column', gap: '16px', width: '312px', align: 'stretch' }}>
            <TripItemCategoryInput
              initialCategory={tripItemInformation.itemType}
              updateInputValue={updateInputValue}
            />
            <TripItemDateInput
              currentCategory={tripItemInformation.itemType}
              initialDate={currentDate.date}
              dates={dates}
              updateInputValue={updateInputValue}
            />
            {tripItemInformation.itemType ? (
              <Input label="장소" name="title" required placeholder="장소를 입력해 주세요" />
            ) : (
              <TripItemTitleInput
                isError={isTitleError}
                initialValue={tripItemInformation.title}
                updateInputValue={updateInputValue}
                disableError={disableTitleError}
              />
            )}
            <TripItemStarRatingInput
              initialRate={tripItemInformation.rating}
              updateInputValue={updateInputValue}
            />
            <TripItemExpenseInput updateInputValue={updateInputValue} />
          </Flex>
          <Flex styles={{ direction: 'column', gap: '16px', width: '312px', align: 'stretch' }}>
            <TripItemMemoInput
              initialValue={tripItemInformation.memo}
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
    </Modal>
  );
};

export default TripItemAddModal;
