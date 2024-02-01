import { Button, Flex, Modal, Theme } from 'hang-log-design-system';

import { UseAddCityForm } from '@/hooks/city/useAddCityForm';

import type { CityFormData } from '@/types/city';

import CloseIcon from '@assets/svg/close-icon.svg?react';

import NameInput from './NameInput/NameInput';
import CountryInput from './CountryInput/CountryInput';
import LatitudeInput from './LatitudeInput/LatitudeInput';
import LongitudeInput from './LongitudeInput/LongitudeInput';

import {
  wrapperStyling,
  formStyling,
  buttonStyling,
  closeButtonStyling,
  closeIconStyling,
} from './CityAddModal.style';

interface CityAddModalProps {
  cityId?: number;
  initialData?: CityFormData;
  isOpen?: boolean;
  onClose: () => void;
}

const CityAddModal = ({ cityId, initialData, isOpen = true, onClose }: CityAddModalProps) => {
  const {
    cityInformation,
    isNameError,
    isCountryError,
    isLatitudeError,
    isLongitudeError,
    disableNameError,
    disableCountryError,
    disableLatitudeError,
    disableLongitudeError,
    updateInputValue,
    handleSubmit,
  } = UseAddCityForm({
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
              isError={isNameError}
              updateInputValue={updateInputValue}
              disableError={disableNameError}
            />
            <CountryInput
              value={cityInformation.country}
              isError={isCountryError}
              updateInputValue={updateInputValue}
              disableError={disableCountryError}
            />
            <LatitudeInput
              value={cityInformation.latitude}
              isError={isLatitudeError}
              updateInputValue={updateInputValue}
              disableError={disableLatitudeError}
            />
            <LongitudeInput
              value={cityInformation.longitude}
              isError={isLongitudeError}
              updateInputValue={updateInputValue}
              disableError={disableLongitudeError}
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
