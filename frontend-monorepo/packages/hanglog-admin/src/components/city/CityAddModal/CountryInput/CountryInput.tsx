import type { ChangeEvent } from 'react';
import { memo } from 'react';

import { Input } from 'hang-log-design-system';

import type { CityFormData } from '@/types/city';

import { CITY_COUNTRY_MAX_LENGTH } from '@/constants/ui';

interface CountryInputProps {
  value: string;
  isError: boolean;
  updateInputValue: <K extends keyof CityFormData>(key: K, value: CityFormData[K]) => void;
  disableError: () => void;
}

const CountryInput = ({ isError, value, updateInputValue, disableError }: CountryInputProps) => {
  const handleCountryChange = (event: ChangeEvent<HTMLInputElement>) => {
    disableError();

    updateInputValue('country', event.target.value);
  };

  return (
    <Input
      label="나라"
      id="country"
      name="country"
      maxLength={CITY_COUNTRY_MAX_LENGTH}
      value={value}
      placeholder="나라를 입력해 주세요"
      supportingText={isError ? '도시의 나라를 입력해 주세요' : undefined}
      isError={isError}
      required
      onChange={handleCountryChange}
    />
  );
};

export default memo(CountryInput);
