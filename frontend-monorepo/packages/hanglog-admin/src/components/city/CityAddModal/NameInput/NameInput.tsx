import type { ChangeEvent } from 'react';
import { memo } from 'react';

import { Input } from 'hang-log-design-system';

import type { CityFormData } from '@/types/city';

import { CITY_NAME_MAX_LENGTH } from '@/constants/ui';

interface NameInputProps {
  value: string;
  isError: boolean;
  updateInputValue: <K extends keyof CityFormData>(key: K, value: CityFormData[K]) => void;
  disableError: () => void;
}

const NameInput = ({ isError, value, updateInputValue, disableError }: NameInputProps) => {
  const handleTitleChange = (event: ChangeEvent<HTMLInputElement>) => {
    disableError();

    updateInputValue('name', event.target.value);
  };

  return (
    <Input
      label="이름"
      id="name"
      name="name"
      maxLength={CITY_NAME_MAX_LENGTH}
      value={value}
      placeholder="이름을 입력해 주세요"
      supportingText={isError ? '도시의 이름을 입력해 주세요' : undefined}
      isError={isError}
      required
      onChange={handleTitleChange}
    />
  );
};

export default memo(NameInput);
