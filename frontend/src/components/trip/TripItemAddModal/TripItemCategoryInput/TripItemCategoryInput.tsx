import type { TripItemFormData } from '@type/tripItem';
import { RadioButton } from 'hang-log-design-system';
import type { ChangeEvent } from 'react';
import { memo } from 'react';

interface TripItemCategoryInputProps {
  itemType: TripItemFormData['itemType'];
  updateInputValue: <K extends keyof TripItemFormData>(key: K, value: TripItemFormData[K]) => void;
  disableError: () => void;
}

const TripItemCategoryInput = ({
  itemType,
  updateInputValue,
  disableError,
}: TripItemCategoryInputProps) => {
  const handleCategoryChange = (event: ChangeEvent<HTMLInputElement>) => {
    const itemType = event.target.id === '장소';
    updateInputValue('itemType', itemType);
    disableError();
  };

  return (
    <RadioButton
      label="카테고리"
      required
      name="trip-item-category"
      options={['장소', '기타']}
      initialCheckedOption={itemType ? '장소' : '기타'}
      onChange={handleCategoryChange}
    />
  );
};

export default memo(TripItemCategoryInput);
