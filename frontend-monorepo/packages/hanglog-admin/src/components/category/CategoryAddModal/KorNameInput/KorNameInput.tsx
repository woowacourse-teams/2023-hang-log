import type { ChangeEvent } from 'react';
import { memo } from 'react';

import { Input } from 'hang-log-design-system';

import type { CategoryData } from '@type/category';

import { CATEGORY_NAME_MAX } from '@constants/ui';

interface KorNameInputProps {
  value: string;
  isError: boolean;
  updateInputValue: <K extends keyof CategoryData>(key: K, value: CategoryData[K]) => void;
  disableError: () => void;
}

const KorNameInput = ({ isError, value, updateInputValue, disableError }: KorNameInputProps) => {
  const handleKorNameChange = (event: ChangeEvent<HTMLInputElement>) => {
    disableError();

    updateInputValue('korName', event.target.value);
  };

  return (
    <Input
      label="국문명"
      id="korName"
      name="korName"
      maxLength={CATEGORY_NAME_MAX}
      value={value}
      placeholder="국문명을 입력해 주세요"
      supportingText={isError ? '카테고리의 국문명을 입력해 주세요' : undefined}
      isError={isError}
      required
      onChange={handleKorNameChange}
    />
  );
};

export default memo(KorNameInput);
