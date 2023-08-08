import { TRIP_ITEM_TITLE_MAX_LENGTH } from '@constants/ui';
import type { TripItemFormData } from '@type/tripItem';
import { Input } from 'hang-log-design-system';
import type { ChangeEvent } from 'react';
import { memo } from 'react';

interface TitleInputProps {
  value: string;
  isError: boolean;
  updateInputValue: <K extends keyof TripItemFormData>(key: K, value: TripItemFormData[K]) => void;
  disableError: () => void;
}

const TitleInput = ({ isError, value, updateInputValue, disableError }: TitleInputProps) => {
  const handleTitleChange = (event: ChangeEvent<HTMLInputElement>) => {
    disableError();

    updateInputValue('title', event.target.value);
  };

  return (
    <Input
      label="제목"
      id="title"
      name="title"
      maxLength={TRIP_ITEM_TITLE_MAX_LENGTH}
      value={value}
      placeholder="제목을 입력해 주세요"
      supportingText={isError ? '일정의 제목을 입력해 주세요' : undefined}
      isError={isError}
      required
      onChange={handleTitleChange}
    />
  );
};

export default memo(TitleInput);
