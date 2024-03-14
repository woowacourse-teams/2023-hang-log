import { Button, Flex, Modal, Theme } from 'hang-log-design-system';

import { useAddCurrencyForm } from '@/currency/hooks/useAddCurrencyForm';

import type { CurrencyFormData } from '@type/currency';

import { currencyKeys } from '@constants/currency';

import CloseIcon from '@assets/svg/close-icon.svg?react';

import {
  buttonStyling,
  closeButtonStyling,
  closeIconStyling,
  formStyling,
  wrapperStyling,
} from './CurrencyAddModal.style';
import CurrencyInput from './CurrencyInput/CurrencyInput';
import DateInput from './DataInput/DateInput';

interface CurrencyAddModalProps {
  currencyId?: number;
  initialData?: CurrencyFormData;
  isOpen?: boolean;
  onClose: () => void;
}

const CurrencyAddModal = (
  { currencyId, initialData, isOpen = true, onClose }: CurrencyAddModalProps
) => {
  const {
    currencyInformation,
    isDateError,
    currencyErrors,
    disableDateError,
    disableCurrencyError,
    updateInputValue,
    handleSubmit,
  } = useAddCurrencyForm({
    currencyId,
    initialData,
    onSuccess: onClose,
  });

  const midIdx = currencyKeys.length / 2;
  const leftCurrency = currencyKeys.slice(0, midIdx);
  const rightCurrency = currencyKeys.slice(midIdx, currencyKeys.length);

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
          <Flex styles={{ justify: 'space-between' }}>
            <Flex
              styles={{
                direction: 'column',
                gap: Theme.spacer.spacing4,
                align: 'stretch',
                width: '45%',
              }}
            >
              <DateInput
                value={currencyInformation.date}
                isError={isDateError}
                updateInputValue={updateInputValue}
                disableError={disableDateError}
              />
              {leftCurrency.map((key) => {
                return (
                  <CurrencyInput
                    currencyType={key}
                    value={Number(currencyInformation[key])}
                    isError={currencyErrors[key]}
                    updateInputValue={updateInputValue}
                    disableError={() => disableCurrencyError(key)}
                  />
                );
              })}
            </Flex>
            <Flex
              styles={{
                direction: 'column',
                gap: Theme.spacer.spacing4,
                align: 'stretch',
                width: '45%',
              }}
            >
              {rightCurrency.map((key) => {
                return (
                  <CurrencyInput
                    currencyType={key}
                    value={Number(currencyInformation[key])}
                    isError={currencyErrors[key]}
                    updateInputValue={updateInputValue}
                    disableError={() => disableCurrencyError(key)}
                  />
                );
              })}
            </Flex>
          </Flex>
          <Button css={buttonStyling} variant="primary">
            환율 정보 {currencyId ? '수정하기' : '추가하기'}
          </Button>
        </form>
      </Modal>
    </>
  );
};

export default CurrencyAddModal;
