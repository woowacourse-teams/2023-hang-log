import type { ChangeEvent } from 'react';
import { memo } from 'react';

import { Input } from 'hang-log-design-system';

import type { CityFormData } from '@/types/city';

import { CITY_LONGITUDE_MAX, CITY_LONGITUDE_MIN } from '@/constants/ui';

interface LongitudeInputProps {
  value: number;
  isError: boolean;
  updateInputValue: <K extends keyof CityFormData>(key: K, value: CityFormData[K]) => void;
  disableError: () => void;
}

const LongitudeInput = ({
  isError,
  value,
  updateInputValue,
  disableError,
}: LongitudeInputProps) => {
  const handleLongitudeChange = (event: ChangeEvent<HTMLInputElement>) => {
    disableError();

    updateInputValue('longitude', Number(event.target.value));
  };

  return (
    <Input
      type="number"
      label="경도"
      id="longitude"
      name="longitude"
      value={value}
      placeholder="0.0"
      min={CITY_LONGITUDE_MIN}
      max={CITY_LONGITUDE_MAX}
      supportingText={isError ? '도시의 경도를 입력해 주세요' : undefined}
      isError={isError}
      required
      onChange={handleLongitudeChange}
    />
  );
};

export default memo(LongitudeInput);
