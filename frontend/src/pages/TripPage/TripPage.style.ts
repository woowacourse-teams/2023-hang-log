import { css } from '@emotion/react';

export const containerStyling = css({
  position: 'relative',
  width: '50vw',

  '@media screen and (max-width: 1200px)': {
    width: '60vw',
  },
});

export const skeletonContainerStyling = css({
  '& > div:first-of-type': {
    borderRadius: 0,
  },
});
