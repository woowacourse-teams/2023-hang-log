import { useTripEditForm } from '@/hooks/trip/useTripEditForm';
import WarningIcon from '@assets/svg/warning-icon.svg';
import type { TripData } from '@type/trip';
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
import { formStyling } from '@components/trip/TripInfoEditModal/TripInfoEditModal.style';

interface TripInfoEditModalProps extends Omit<TripData, 'dayLogs'> {
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
    handleSubmit,
  } = useTripEditForm(information, onClose);

  return (
    <Modal isOpen={isOpen} closeModal={onClose} hasCloseButton>
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
            initialDateRange={{ start: tripInfo.startDate, end: tripInfo.endDate }}
            updateDateInfo={updateDateInfo}
          />
          <Flex styles={{ width: '400px', align: 'center', gap: '10px' }}>
            <WarningIcon />
            <SupportingText>
              방문 기간을 단축하면 마지막 날짜부터 작성한 기록들이 <br />
              삭제됩니다.
            </SupportingText>
          </Flex>
        </Flex>
        <Flex styles={{ direction: 'column', gap: '4px' }}>
          <Input
            label="여행 제목"
            value={tripInfo.title}
            required
            isError={isTitleError}
            onChange={updateInputValue('title')}
          />
          {isTitleError && (
            <SupportingText isError={isTitleError}>여행 제목을 입력하세요</SupportingText>
          )}
        </Flex>
        <Textarea
          label="여행 설명"
          value={tripInfo.description ?? ''}
          onChange={updateInputValue('description')}
        />
        <ImageUploadInput
          label="대표 이미지 업로드"
          imageUrls={tripInfo.imageUrl === null ? null : [tripInfo.imageUrl]}
          imageAltText="여행 대표 업로드 이미지"
          maxUploadCount={1}
          onRemove={() => {}}
        />
        <Button variant="primary">여행 정보 수정</Button>
      </form>
    </Modal>
  );
};

export default TripInfoEditModal;
