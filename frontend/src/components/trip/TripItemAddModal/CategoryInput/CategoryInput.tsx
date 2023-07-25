import { TRIP_ITEM_ADD_CATEGORIES } from '@constants/trip';
import type { TripItemFormData } from '@type/tripItem';
import { RadioButton } from 'hang-log-design-system';
import type { ChangeEvent } from 'react';
import { memo } from 'react';

interface CategoryInputProps {
  itemType: TripItemFormData['itemType'];
  updateInputValue: <K extends keyof TripItemFormData>(key: K, value: TripItemFormData[K]) => void;
  disableError: () => void;
}

const CategoryInput = ({ itemType, updateInputValue, disableError }: CategoryInputProps) => {
  const handleCategoryChange = (event: ChangeEvent<HTMLInputElement>) => {
    const itemType = event.target.id === TRIP_ITEM_ADD_CATEGORIES.SPOT;
    updateInputValue('itemType', itemType);
    disableError();
  };

  return (
    <RadioButton
      label="카테고리"
      required
      name="trip-item-category"
      options={Object.values(TRIP_ITEM_ADD_CATEGORIES)}
      initialCheckedOption={
        itemType ? TRIP_ITEM_ADD_CATEGORIES.SPOT : TRIP_ITEM_ADD_CATEGORIES.NON_SPOT
      }
      onChange={handleCategoryChange}
    />
  );
};

export default memo(CategoryInput);
