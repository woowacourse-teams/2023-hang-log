import type { TripData } from '@type/trip';
import { Button, ImageUploadInput, Input, Modal } from 'hang-log-design-system';
import { useCallback, useState } from 'react';
import type { FormEvent } from 'react';

import CitySearchBar from '@components/common/CitySearchBar/CitySearchBar';
import DateInput from '@components/common/DateInput/DateInput';
import { formStyling } from '@components/trip/TripInfoEditModal/TripInfoEditModal.style';

interface TripInfoEditModalProps extends Omit<TripData, 'dayLogs'> {
  isOpen: boolean;
  onClose: () => void;
}

const TripInfoEditModal = ({ isOpen, onClose, ...information }: TripInfoEditModalProps) => {
  const { id, title, cities, startDate, endDate, description, imageUrl } = information;

  return (
    <Modal isOpen={isOpen} closeModal={() => {}} hasCloseButton>
      <form css={formStyling}>
        <CitySearchBar required initialCityTags={cities} setCityData={() => {}} />
        <DateInput
          required
          initialDateRange={{ start: startDate, end: endDate }}
          setDateData={() => {}}
        />
        <Input label="여행 제목" value={title} required />
        <Input label="여행 설명" value={description ?? ''} />
        <ImageUploadInput
          label="대표 이미지 업로드"
          imageUrls={imageUrl === null ? null : [imageUrl]}
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
