import type { StarRatingData, TripItemFormType } from '@type/tripItem';
import { StarRatingInput, useStarRatingInput } from 'hang-log-design-system';
import { memo } from 'react';

interface TripItemStarRatingInputProps {
  initialRate: StarRatingData | null;
  updateInputValue: <K extends keyof TripItemFormType>(key: K, value: TripItemFormType[K]) => void;
}

const TripItemStarRatingInput = ({
  initialRate,
  updateInputValue,
}: TripItemStarRatingInputProps) => {
  const handleRatingChange = (rate: StarRatingData) => {
    const newRate = rate || null;
    updateInputValue('rating', newRate);
  };

  const { starRate, handleStarClick, handleStarHover, handleStarHoverOut } = useStarRatingInput(
    initialRate ?? 0,
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
