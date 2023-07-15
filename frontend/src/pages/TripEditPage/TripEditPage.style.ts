import { css } from '@emotion/react';

export const containerStyling = css({
  position: 'relative',
  width: '50vw',

  '@media screen and (max-width: 1200px)': {
    width: '60vw',
  },
});

export const addButtonStyling = css({
  position: 'fixed',
  right: 'calc(50vw + 50px)',
  bottom: '50px',
});

export const skeletonContainerStyling = css({
  '& > div:first-of-type': {
    borderRadius: 0,
  },
});
