import type { ComponentPropsWithoutRef } from 'react';

import { Flex } from 'hang-log-design-system';

import Star from '@components/common/StarRating/Star/Star';
import { containerStyling } from '@components/common/StarRating/StarRating.style';

import { STAR_RATING_LENGTH } from '@constants/ui';

interface StarRatingProps extends ComponentPropsWithoutRef<'div'> {
  rate: 0 | 0.5 | 1 | 1.5 | 2 | 2.5 | 3 | 3.5 | 4 | 4.5 | 5;
}

const StarRating = ({ rate, ...attributes }: StarRatingProps) => {
  return (
    <Flex css={containerStyling} styles={{ gap: '6px' }} {...attributes}>
      {Array.from({ length: STAR_RATING_LENGTH }, (_, index) => {
        const isFilled = index < rate;
        const isHalf = rate - index === 0.5;

        return (
          <Flex key={index}>
            <Star isFilled={isFilled} isHalf={isHalf} />
          </Flex>
        );
      })}
    </Flex>
  );
};

export default StarRating;
