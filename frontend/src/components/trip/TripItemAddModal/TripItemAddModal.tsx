import { useRecoilValue } from 'recoil';

import { Button, Flex, Modal, Theme } from 'hang-log-design-system';

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

import { useAddTripItemForm } from '@hooks/trip/useAddTripItemForm';

import { mediaQueryMobileState } from '@store/mediaQuery';

import type { TripItemFormData } from '@type/tripItem';

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

  const { tripItemInformation, isTitleError, updateInputValue, disableTitleError, handleSubmit } =
    useAddTripItemForm({
      tripId,
      initialDayLogId: dayLogId,
      itemId,
      initialData,
      onSuccess: onClose,
    });

  return (
    <Modal css={wrapperStyling} isOpen={isOpen} closeModal={onClose} hasCloseButton>
      <GoogleMapWrapper>
        <form css={formStyling} onSubmit={handleSubmit} noValidate>
          <Flex styles={{ gap: Theme.spacer.spacing4, direction: isMobile ? 'column' : 'row' }}>
            <Flex
              styles={{
                direction: 'column',
                gap: '16px',
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
              <ImageInput
                initialImageUrls={tripItemInformation.imageUrls}
                updateInputValue={updateInputValue}
              />
            </Flex>
          </Flex>
          <Button variant="primary">일정 기록 {itemId ? '수정하기' : '추가하기'}</Button>
        </form>
      </GoogleMapWrapper>
    </Modal>
  );
};

export default TripItemAddModal;
