import { Input } from 'hang-log-design-system';
import type { ChangeEvent } from 'react';
import { memo } from 'react';

import type { CurrencyFormData } from '@type/currency';

import { CURRENCY_DATE_MAX_LENGTH } from '@constants/ui';

interface DateInputProps {
  value: string;
  isError: boolean;
  updateInputValue: <K extends keyof CurrencyFormData>(key: K, value: CurrencyFormData[K]) => void;
  disableError: () => void;
}

const DateInput = ({ isError, value, updateInputValue, disableError }: DateInputProps) => {
  const handleDateChange = (event: ChangeEvent<HTMLInputElement>) => {
    disableError();

    updateInputValue('date', event.target.value);
  };

  return (
    <Input
      label="날짜"
      id="date"
      name="date"
      maxLength={CURRENCY_DATE_MAX_LENGTH}
      value={value}
      placeholder="YYYY-MM-DD"
      supportingText={isError ? '올바른 형식으로 날짜를 입력해 주세요' : undefined}
      isError={isError}
      required
      onChange={handleDateChange}
    />
  );
};

export default memo(DateInput);
