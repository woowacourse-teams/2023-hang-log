import { useEditTripInfo } from '@/hooks/newTrip/useEditTripInfo';
import type { TripData, TripPutData } from '@type/trip';
import { Button, ImageUploadInput, Input, Modal } from 'hang-log-design-system';
import type { ChangeEvent } from 'react';

import CitySearchBar from '@components/common/CitySearchBar/CitySearchBar';
import DateInput from '@components/common/DateInput/DateInput';
import { formStyling } from '@components/trip/TripInfoEditModal/TripInfoEditModal.style';

interface TripInfoEditModalProps extends Omit<TripData, 'dayLogs'> {
  isOpen: boolean;
  onClose: () => void;
}

const TripInfoEditModal = ({ isOpen, onClose, ...information }: TripInfoEditModalProps) => {
  const { tripInfo, updateInputValue, setCityData, setDateData, handleSubmit } =
    useEditTripInfo(information);

  const handleChangeValue = (key: keyof TripPutData) => (e: ChangeEvent<HTMLInputElement>) => {
    updateInputValue(key, e.currentTarget.value);
  };

  return (
    <Modal isOpen={isOpen} closeModal={onClose} hasCloseButton>
      <form onSubmit={handleSubmit} css={formStyling}>
        <CitySearchBar required initialCityTags={information.cities} setCityData={setCityData} />
        <DateInput
          required
          initialDateRange={{ start: tripInfo.startDate, end: tripInfo.endDate }}
          setDateData={setDateData}
        />
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
          imageAltText="여행 대표 이미지 업로드"
          maxUploadCount={1}
          onRemove={() => {}}
        />
        <Button variant="primary" type="submit">
          여행 정보 수정
        </Button>
      </form>
    </Modal>
  );
};

export default TripInfoEditModal;
