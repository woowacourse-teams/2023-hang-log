import type { ChangeEvent } from 'react';
import { memo } from 'react';

import { Input } from 'hang-log-design-system';

import type { CityFormData } from '@/types/city';

import { CITY_LATITUDE_MAX, CITY_LATITUDE_MIN } from '@/constants/ui';

interface LatitudeInputProps {
  value: number;
  isError: boolean;
  updateInputValue: <K extends keyof CityFormData>(key: K, value: CityFormData[K]) => void;
  disableError: () => void;
}

const LatitudeInput = ({ isError, value, updateInputValue, disableError }: LatitudeInputProps) => {
  const handleLatitudeChange = (event: ChangeEvent<HTMLInputElement>) => {
    disableError();

    updateInputValue('latitude', Number(event.target.value));
  };

  return (
    <Input
      type="number"
      label="위도"
      id="latitude"
      name="latitude"
      value={value}
      placeholder="0.0"
      min={CITY_LATITUDE_MIN}
      max={CITY_LATITUDE_MAX}
      supportingText={isError ? '도시의 위도를 입력해 주세요' : undefined}
      isError={isError}
      required
      onChange={handleLatitudeChange}
    />
  );
};

export default memo(LatitudeInput);
