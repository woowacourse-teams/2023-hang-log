import type { TripItemFormType } from '@type/tripItem';
import { Textarea } from 'hang-log-design-system';
import type { ChangeEvent } from 'react';
import { memo, useState } from 'react';

import { textareaStyling } from '@components/trip/TripItemAddModal/TripItemMemoInput/TripItemMemoInput.style';

interface TripItemMemoInputProps {
  initialValue: TripItemFormType['memo'];
  updateInputValue: <K extends keyof TripItemFormType>(key: K, value: TripItemFormType[K]) => void;
}

const TripItemMemoInput = ({ initialValue, updateInputValue }: TripItemMemoInputProps) => {
  const [value, setValue] = useState<string>(initialValue ?? '');

  const handleMemoChange = (event: ChangeEvent<HTMLTextAreaElement>) => {
    setValue(event.target.value);
    updateInputValue('memo', event.target.value);
  };

  return (
    <Textarea
      css={textareaStyling}
      label="메모"
      name="memo"
      value={value}
      placeholder="메모를 입력해 주세요"
      onChange={handleMemoChange}
    />
  );
};

export default memo(TripItemMemoInput);
