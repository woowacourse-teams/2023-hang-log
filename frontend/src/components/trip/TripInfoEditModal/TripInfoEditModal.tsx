import type { TripData, TripFormData } from '@type/trip';
import { Button, ImageUploadInput, Input, Modal, SupportingText } from 'hang-log-design-system';
import type { ChangeEvent } from 'react';

import { useTripInfoForm } from '@hooks/trip/useTripInfoForm';

import CitySearchBar from '@components/common/CitySearchBar/CitySearchBar';
import DateInput from '@components/common/DateInput/DateInput';
import {
  cityInputErrorTextStyling,
  dateInputTextStyling,
  formStyling,
} from '@components/trip/TripInfoEditModal/TripInfoEditModal.style';

interface TripInfoEditModalProps extends Omit<TripData, 'dayLogs'> {
  isOpen: boolean;
  onClose: () => void;
}

const TripInfoEditModal = ({ isOpen, onClose, ...information }: TripInfoEditModalProps) => {
  const {
    tripInfo,
    isCityInputError,
    updateInputValue,
    updateCityInfo,
    updateDateInfo,
    handleSubmit,
  } = useTripInfoForm(information, onClose);

  const handleChangeValue = (key: keyof TripFormData) => (e: ChangeEvent<HTMLInputElement>) => {
    updateInputValue(key, e.currentTarget.value);
  };

  return (
    <Modal isOpen={isOpen} closeModal={onClose} hasCloseButton>
      <form onSubmit={handleSubmit} css={formStyling}>
        <CitySearchBar
          required
          initialCities={information.cities}
          updateCityInfo={updateCityInfo}
        />
        {isCityInputError && (
          <SupportingText css={cityInputErrorTextStyling}>
            방문 도시는 최소 한개 이상 선택해야 합니다
          </SupportingText>
        )}
        <DateInput
          required
          initialDateRange={{ start: tripInfo.startDate, end: tripInfo.endDate }}
          updateDateInfo={updateDateInfo}
        />
        <SupportingText css={dateInputTextStyling}>
          ⚠︎ 방문 기간을 단축하면 마지막 날짜부터 작성한 기록들이 <br />
          삭제됩니다.
        </SupportingText>
        <Input
          label="여행 제목"
          value={tripInfo.title}
          required
          onChange={handleChangeValue('title')}
        />
        <Input
          label="여행 설명"
          value={tripInfo.description ?? ''}
          onChange={handleChangeValue('description')}
        />
        <ImageUploadInput
          label="대표 이미지 업로드"
          imageUrls={tripInfo.imageUrl === null ? null : [tripInfo.imageUrl]}
          imageAltText="여행 대표 업로드 이미지 "
          maxUploadCount={1}
          onRemove={() => {}}
        />
        <Button variant="primary">여행 정보 수정</Button>
      </form>
    </Modal>
  );
};

export default TripInfoEditModal;
