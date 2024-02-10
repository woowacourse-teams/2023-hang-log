import type { ChangeEvent } from 'react';
import { memo } from 'react';

import { Input } from 'hang-log-design-system';

import type { CurrencyFormData } from '@type/currency';

import { CURRENCY_MIN, CURRENCY_MAX } from '@constants/ui';

interface CurrencyInputProps {
  currencyType: string;
  value: number;
  isError: boolean;
  updateInputValue: <K extends keyof CurrencyFormData>(
    key: string,
    value: CurrencyFormData[K]
  ) => void;
  disableError: () => void;
}

const CurrencyInput = ({
  currencyType,
  isError,
  value,
  updateInputValue,
  disableError,
}: CurrencyInputProps) => {
  const handleCurrencyChange = (event: ChangeEvent<HTMLInputElement>) => {
    disableError();

    updateInputValue(currencyType, Number(event.target.value));
  };

  return (
    <Input
      type="number"
      label={currencyType === 'jpy' ? 'JPY(100)' : currencyType.toUpperCase()}
      id={currencyType}
      name={currencyType}
      value={value}
      placeholder="0.0"
      min={CURRENCY_MIN}
      max={CURRENCY_MAX}
      supportingText={
        isError ? `${currencyType.toUpperCase()} 환율 정보를 입력해 주세요` : undefined
      }
      isError={isError}
      required
      onChange={handleCurrencyChange}
    />
  );
};

export default memo(CurrencyInput);
