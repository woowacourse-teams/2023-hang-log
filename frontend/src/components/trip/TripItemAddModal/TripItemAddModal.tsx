import type { TripItemFormData } from '@type/tripItem';
import { Button, Flex, Modal, Theme, Toast, useOverlay } from 'hang-log-design-system';

import { useAddTripItemForm } from '@hooks/trip/useAddTripItemForm';
import { useTripDates } from '@hooks/trip/useTripDates';

import GoogleMapWrapper from '@components/common/GoogleMapWrapper/GoogleMapWrapper';
import CategoryInput from '@components/trip/TripItemAddModal/CategoryInput/CategoryInput';
import DateInput from '@components/trip/TripItemAddModal/DateInput/DateInput';
import ExpenseInput from '@components/trip/TripItemAddModal/ExpenseInput/ExpenseInput';
import ImageInput from '@components/trip/TripItemAddModal/ImageInput/ImageInput';
import MemoInput from '@components/trip/TripItemAddModal/MemoInput/MemoInput';
import PlaceInput from '@components/trip/TripItemAddModal/PlaceInput/PlaceInput';
import StarRatingInput from '@components/trip/TripItemAddModal/StarRatingInput/StarRatingInput';
import TitleInput from '@components/trip/TripItemAddModal/TitleInput/TitleInput';
import {
  formStyling,
  wrapperStyling,
} from '@components/trip/TripItemAddModal/TripItemAddModal.style';

interface TripItemAddModalProps {
  tripId: number;
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
  const { dates } = useTripDates(tripId);
  const { isOpen: isErrorTostOpen, open: openErrorToast, close: closeErrorToast } = useOverlay();
  const { tripItemInformation, isTitleError, updateInputValue, disableTitleError, handleSubmit } =
    useAddTripItemForm({
      tripId,
      initialDayLogId: dayLogId,
      itemId,
      initialData,
      onSuccess: onClose,
      onError: openErrorToast,
    });

  return (
    <>
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
                />
                <ExpenseInput
                  initialExpenseValue={tripItemInformation.expense}
                  updateInputValue={updateInputValue}
                />
              </Flex>
              <Flex styles={{ direction: 'column', gap: '16px', width: '312px', align: 'stretch' }}>
                <MemoInput value={tripItemInformation.memo} updateInputValue={updateInputValue} />
                <ImageInput
                  initialImageUrls={tripItemInformation.imageUrls}
                  updateInputValue={updateInputValue}
                />
              </Flex>
            </Flex>
            <Button type="submit" variant="primary">
              일정 기록 추가하기
            </Button>
          </form>
        </GoogleMapWrapper>
      </Modal>
      {isErrorTostOpen && (
        <Toast variant="error" closeToast={closeErrorToast}>
          아이템 {itemId ? '수정을' : '추가를'} 실패했습니다. 잠시 후 다시 시도해 주세요.
        </Toast>
      )}
    </>
  );
};

export default TripItemAddModal;
