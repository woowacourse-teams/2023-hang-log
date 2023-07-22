import type { TripItemFormData } from '@type/tripItem';
import { Textarea } from 'hang-log-design-system';
import type { ChangeEvent } from 'react';
import { memo } from 'react';

import { textareaStyling } from '@components/trip/TripItemAddModal/TripItemMemoInput/TripItemMemoInput.style';

interface TripItemMemoInputProps {
  value: TripItemFormData['memo'];
  updateInputValue: <K extends keyof TripItemFormData>(key: K, value: TripItemFormData[K]) => void;
}

const TripItemMemoInput = ({ value, updateInputValue }: TripItemMemoInputProps) => {
  const handleMemoChange = (event: ChangeEvent<HTMLTextAreaElement>) => {
    updateInputValue('memo', event.target.value);
  };

  return (
    <Textarea
      css={textareaStyling}
      label="메모"
      name="memo"
      value={value ?? ''}
      placeholder="메모를 입력해 주세요"
      onChange={handleMemoChange}
    />
  );
};

export default memo(TripItemMemoInput);
