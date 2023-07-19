import type { TripItemFormType } from '@type/tripItem';
import { Input } from 'hang-log-design-system';
import type { ChangeEvent } from 'react';
import { memo, useState } from 'react';

interface TripItemTitleInputProps {
  initialValue: string;
  isError: boolean;
  updateInputValue: <K extends keyof TripItemFormType>(key: K, value: TripItemFormType[K]) => void;
  disableError: () => void;
}

const TripItemTitleInput = ({
  isError,
  initialValue,
  updateInputValue,
  disableError,
}: TripItemTitleInputProps) => {
  const [value, setValue] = useState(initialValue);

  const handleTitleChange = (event: ChangeEvent<HTMLInputElement>) => {
    if (isError) disableError();

    setValue(event.target.value);
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
