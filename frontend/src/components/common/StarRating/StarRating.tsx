import EmptyStarIcon from '@assets/svg/empty-star-icon.svg';
import FilledStarIcon from '@assets/svg/filled-star-icon.svg';
import { STAR_RATING_LENGTH } from '@constants/ui';
import { Flex } from 'hang-log-design-system';
import type { ComponentPropsWithoutRef } from 'react';
import { Fragment } from 'react';

import { getContainerStyling } from '@components/common/StarRating/StarRating.style';

interface StarRatingProps extends ComponentPropsWithoutRef<'div'> {
  rate: number;
  size?: number;
  gap?: number;
}

const StarRating = ({ rate, size = 20, gap = 6, ...attributes }: StarRatingProps) => {
  return (
    <Flex css={getContainerStyling(size)} styles={{ gap: `${gap}px` }} {...attributes}>
      {Array.from({ length: STAR_RATING_LENGTH }, (_, index) => (
        <Fragment key={index}>{index < rate ? <FilledStarIcon /> : <EmptyStarIcon />}</Fragment>
      ))}
    </Flex>
  );
};

export default StarRating;
