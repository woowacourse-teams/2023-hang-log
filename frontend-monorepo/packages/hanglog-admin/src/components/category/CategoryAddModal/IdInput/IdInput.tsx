import type { ChangeEvent } from 'react';
import { memo } from 'react';

import { Input } from 'hang-log-design-system';

import type { CategoryData } from '@/types/category';

import { CATEGORY_ID_MAX, CATEGORY_ID_MIN } from '@/constants/ui';

interface IdInputProps {
  value: number;
  isError: boolean;
  updateInputValue: <K extends keyof CategoryData>(key: K, value: CategoryData[K]) => void;
  disableError: () => void;
}

const IdInput = ({ isError, value, updateInputValue, disableError }: IdInputProps) => {
  const handleIdChange = (event: ChangeEvent<HTMLInputElement>) => {
    disableError();

    updateInputValue('id', Number(event.target.value));
  };

  return (
    <Input
      type="number"
      label="아이디"
      id="id"
      name="id"
      value={value}
      placeholder="000"
      min={CATEGORY_ID_MIN}
      max={CATEGORY_ID_MAX}
      supportingText={isError ? '카테고리의 아이디를 입력해 주세요' : undefined}
      isError={isError}
      required
      onChange={handleIdChange}
    />
  );
};

export default memo(IdInput);
