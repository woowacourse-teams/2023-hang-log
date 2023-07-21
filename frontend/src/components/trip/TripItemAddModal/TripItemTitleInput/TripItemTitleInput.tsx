import type { TripItemFormType } from '@type/tripItem';
import { Input } from 'hang-log-design-system';
import type { ChangeEvent } from 'react';
import { memo } from 'react';

interface TripItemTitleInputProps {
  value: string;
  isError: boolean;
  updateInputValue: <K extends keyof TripItemFormType>(key: K, value: TripItemFormType[K]) => void;
  disableError: () => void;
}

const TripItemTitleInput = ({
  isError,
  value,
  updateInputValue,
  disableError,
}: TripItemTitleInputProps) => {
  const handleTitleChange = (event: ChangeEvent<HTMLInputElement>) => {
    if (isError) disableError();

    updateInputValue('title', event.target.value);
  };

  return (
    <Input
      label="제목"
      name="title"
      value={value}
      placeholder="제목을 입력해 주세요"
      supportingText={isError ? '일정의 제목을 입력해 주세요' : undefined}
      isError={isError}
      required
      onChange={handleTitleChange}
    />
  );
};

export default memo(TripItemTitleInput);
