import type { StarRatingData, TripItemFormData } from '@type/tripItem';
import { StarRatingInput, useStarRatingInput } from 'hang-log-design-system';
import { memo } from 'react';

interface TripItemStarRatingInputProps {
  rating: StarRatingData | null;
  updateInputValue: <K extends keyof TripItemFormData>(key: K, value: TripItemFormData[K]) => void;
}

const TripItemStarRatingInput = ({ rating, updateInputValue }: TripItemStarRatingInputProps) => {
  const handleRatingChange = (rate: StarRatingData) => {
    const newRate = rate || null;
    updateInputValue('rating', newRate);
  };

  const { starRate, handleStarClick, handleStarHover, handleStarHoverOut } = useStarRatingInput(
    rating ?? 0,
    handleRatingChange
  );

  return (
    <StarRatingInput
      label="별점"
      size={32}
      gap={8}
      rate={starRate}
      onStarClick={handleStarClick}
      onStarHover={handleStarHover}
      onStarHoverOut={handleStarHoverOut}
    />
  );
};

export default memo(TripItemStarRatingInput);
