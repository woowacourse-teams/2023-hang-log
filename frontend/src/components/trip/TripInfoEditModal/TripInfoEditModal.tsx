import { useCallback } from 'react';

import {
  Button,
  Flex,
  ImageUploadInput,
  Input,
  Modal,
  SupportingText,
  Textarea,
} from 'hang-log-design-system';

import CitySearchBar from '@components/common/CitySearchBar/CitySearchBar';
import DateInput from '@components/common/DateInput/DateInput';
import {
  buttonStyling,
  dateInputSupportingText,
  formStyling,
  textareaStyling,
  wrapperStyling,
} from '@components/trip/TripInfoEditModal/TripInfoEditModal.style';

import { useMultipleImageUpload } from '@hooks/common/useMultipleImageUpload';
import { useTripEditForm } from '@hooks/trip/useTripEditForm';

import type { TripData } from '@type/trip';

import { TRIP_DESCRIPTION_MAX_LENGTH, TRIP_TITLE_MAX_LENGTH } from '@constants/ui';

import WarningIcon from '@assets/svg/warning-icon.svg';

interface TripInfoEditModalProps extends Omit<TripData, 'dayLogs' | 'sharedCode' | 'isPublished'> {
  isOpen: boolean;
  onClose: () => void;
}

const TripInfoEditModal = ({ isOpen, onClose, ...information }: TripInfoEditModalProps) => {
  const {
    tripInfo,
    isCityInputError,
    isTitleError,
    updateInputValue,
    updateCityInfo,
    updateDateInfo,
    updateCoverImage,
    handleSubmit,
  } = useTripEditForm(information, onClose);

  const handleImageNameChange = useCallback(
    (imageNames: string[]) => {
      updateCoverImage(imageNames[0]);
    },
    [updateCoverImage]
  );

  const { uploadedImageUrls, isImageUploading, handleImageUpload, handleImageRemoval } =
    useMultipleImageUpload({
      initialImageNames: information.imageName === null ? [] : [information.imageName],
      updateFormImage: handleImageNameChange,
      maxUploadCount: 1,
    });

  return (
    <Modal
      isOpen={isOpen}
      closeModal={onClose}
      isBackdropClosable={false}
      hasCloseButton
      css={wrapperStyling}
    >
      <form onSubmit={handleSubmit} css={formStyling} noValidate>
        <Flex styles={{ direction: 'column', gap: '4px' }}>
          <CitySearchBar
            required
            initialCities={information.cities}
            updateCityInfo={updateCityInfo}
          />
          {isCityInputError && (
            <SupportingText isError={isCityInputError}>
              방문 도시는 최소 한개 이상 선택해야 합니다
            </SupportingText>
          )}
        </Flex>
        <Flex styles={{ direction: 'column', gap: '4px' }}>
          <DateInput
            required
            initialDateRange={{ startDate: tripInfo.startDate, endDate: tripInfo.endDate }}
            updateDateInfo={updateDateInfo}
          />
          <Flex styles={{ width: '100%', align: 'center', gap: '10px' }}>
            <WarningIcon />
            <SupportingText css={dateInputSupportingText}>
              방문 기간을 단축하면 마지막 날짜부터 작성한 기록들이 삭제됩니다.
            </SupportingText>
          </Flex>
        </Flex>
        <Input
          required
          label="여행 제목"
          id="title"
          maxLength={TRIP_TITLE_MAX_LENGTH}
          value={tripInfo.title}
          isError={isTitleError}
          supportingText={isTitleError ? '여행 제목을 입력하세요' : undefined}
          onChange={updateInputValue('title')}
        />
        <Textarea
          id="description"
          label="여행 설명"
          maxLength={TRIP_DESCRIPTION_MAX_LENGTH}
          value={tripInfo.description ?? ''}
          onChange={updateInputValue('description')}
          css={textareaStyling}
        />
        <ImageUploadInput
          id="cover-image-upload"
          label="대표 이미지 업로드"
          imageAltText="여행 대표 업로드 이미지"
          imageUrls={uploadedImageUrls}
          maxUploadCount={1}
          onChange={handleImageUpload}
          onRemove={handleImageRemoval}
        />
        <Button
          variant={isImageUploading ? 'default' : 'primary'}
          disabled={isImageUploading}
          css={buttonStyling}
        >
          여행 정보 수정하기
        </Button>
      </form>
    </Modal>
  );
};

export default TripInfoEditModal;
