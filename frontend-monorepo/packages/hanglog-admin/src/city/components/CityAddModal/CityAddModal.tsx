import { Button, Flex, Modal, Theme } from 'hang-log-design-system';

import { useAddCityForm } from '@/city/hooks/useAddCityForm';

import type { CityFormData } from '@type/city';

import CloseIcon from '@assets/svg/close-icon.svg?react';

import {
  buttonStyling,
  closeButtonStyling,
  closeIconStyling,
  formStyling,
  wrapperStyling,
} from './CityAddModal.style';
import CountryInput from './CountryInput/CountryInput';
import LatitudeInput from './LatitudeInput/LatitudeInput';
import LongitudeInput from './LongitudeInput/LongitudeInput';
import NameInput from './NameInput/NameInput';

interface CityAddModalProps {
  cityId?: number;
  initialData?: CityFormData;
  isOpen?: boolean;
  onClose: () => void;
}

const CityAddModal = ({ cityId, initialData, isOpen = true, onClose }: CityAddModalProps) => {
  const { cityInformation, errors, disableError, updateInputValue, handleSubmit } = useAddCityForm({
    cityId,
    initialData,
    onSuccess: onClose,
  });

  return (
    <>
      <Modal
        css={wrapperStyling}
        isOpen={isOpen}
        closeModal={onClose}
        isBackdropClosable={false}
        hasCloseButton={false}
      >
        <button
          type="button"
          aria-label="모달 닫기 버튼"
          onClick={onClose}
          css={closeButtonStyling}
        >
          <CloseIcon css={closeIconStyling} />
        </button>
        <form css={formStyling} onSubmit={handleSubmit} noValidate>
          <Flex styles={{ direction: 'column', gap: Theme.spacer.spacing3, align: 'stretch' }}>
            <NameInput
              value={cityInformation.name}
              isError={errors.isNameError}
              updateInputValue={updateInputValue}
              disableError={() => disableError('isNameError')}
            />
            <CountryInput
              value={cityInformation.country}
              isError={errors.isCountryError}
              updateInputValue={updateInputValue}
              disableError={() => disableError('isCountryError')}
            />
            <LatitudeInput
              value={cityInformation.latitude}
              isError={errors.isLatitudeError}
              updateInputValue={updateInputValue}
              disableError={() => disableError('isLatitudeError')}
            />
            <LongitudeInput
              value={cityInformation.longitude}
              isError={errors.isLongitudeError}
              updateInputValue={updateInputValue}
              disableError={() => disableError('isLongitudeError')}
            />
          </Flex>
          <Button css={buttonStyling} variant="primary">
            도시 {cityId ? '수정하기' : '추가하기'}
          </Button>
        </form>
      </Modal>
    </>
  );
};

export default CityAddModal;
