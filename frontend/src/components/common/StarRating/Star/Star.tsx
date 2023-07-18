import EmptyRightStarIcon from '@assets/svg/empty-right-star-icon.svg';
import EmptyStarIcon from '@assets/svg/empty-star-icon.svg';
import FilledLeftStarIcon from '@assets/svg/filled-left-star-icon.svg';
import FilledStarIcon from '@assets/svg/filled-star-icon.svg';

interface StarProps {
  isFilled: boolean;
  isHalf: boolean;
}

const Star = ({ isFilled, isHalf }: StarProps) => {
  if (isHalf) {
    return (
      <>
        <FilledLeftStarIcon />
        <EmptyRightStarIcon />
      </>
    );
  }

  return <>{isFilled ? <FilledStarIcon /> : <EmptyStarIcon />}</>;
};

export default Star;
