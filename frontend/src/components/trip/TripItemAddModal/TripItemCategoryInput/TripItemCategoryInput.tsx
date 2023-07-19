import type { TripItemFormType } from '@type/tripItem';
import { RadioButton } from 'hang-log-design-system';
import type { ChangeEvent } from 'react';
import { memo } from 'react';

interface TripItemCategoryInputProps {
  initialCategory: TripItemFormType['itemType'];
  updateInputValue: <K extends keyof TripItemFormType>(key: K, value: TripItemFormType[K]) => void;
}

const TripItemCategoryInput = ({
  initialCategory,
  updateInputValue,
}: TripItemCategoryInputProps) => {
  const handleCategoryChange = (event: ChangeEvent<HTMLInputElement>) => {
    const itemType = event.target.id === '장소';
    updateInputValue('itemType', itemType);
  };

  return (
    <RadioButton
      label="카테고리"
      required
      name="trip-item-category"
      options={['장소', '기타']}
      initialCheckedOption={initialCategory ? '장소' : '기타'}
      onChange={handleCategoryChange}
    />
  );
};

export default memo(TripItemCategoryInput);
