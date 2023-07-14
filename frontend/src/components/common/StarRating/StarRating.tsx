import EmptyStarIcon from '@assets/svg/empty-star-icon.svg';
import FilledStarIcon from '@assets/svg/filled-star-icon.svg';
import { Flex } from 'hang-log-design-system';
import type { ComponentPropsWithoutRef } from 'react';
import { Fragment } from 'react';

import { getContainerStyling } from './StarRating.style';

interface StarRatingProps extends ComponentPropsWithoutRef<'div'> {
  rate: number;
  size?: number;
  gap?: number;
}

const StarRating = ({ rate, size = 20, gap = 6, ...attributes }: StarRatingProps) => {
  return (
    <Flex css={getContainerStyling(size, gap)} styles={{ gap: `${gap}px` }} {...attributes}>
      {Array.from({ length: 5 }, (_, index) => (
        <Fragment key={index}>{index < rate ? <FilledStarIcon /> : <EmptyStarIcon />}</Fragment>
      ))}
    </Flex>
  );
};

export default StarRating;
