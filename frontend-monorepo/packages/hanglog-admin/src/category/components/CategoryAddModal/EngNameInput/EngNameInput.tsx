import { Input } from 'hang-log-design-system';
import type { ChangeEvent } from 'react';
import { memo } from 'react';

import type { CategoryData } from '@type/category';

import { CATEGORY_NAME_MAX } from '@constants/ui';

interface EngNameInputProps {
  value: string;
  isError: boolean;
  updateInputValue: <K extends keyof CategoryData>(key: K, value: CategoryData[K]) => void;
  disableError: () => void;
}

const EngNameInput = ({ isError, value, updateInputValue, disableError }: EngNameInputProps) => {
  const handleEngNameChange = (event: ChangeEvent<HTMLInputElement>) => {
    disableError();

    updateInputValue('engName', event.target.value);
  };

  return (
    <Input
      label="영문명"
      id="engName"
      name="engName"
      maxLength={CATEGORY_NAME_MAX}
      value={value}
      placeholder="영문명을 입력해 주세요"
      supportingText={isError ? '카테고리의 영문명을 입력해 주세요' : undefined}
      isError={isError}
      required
      onChange={handleEngNameChange}
    />
  );
};

export default memo(EngNameInput);
