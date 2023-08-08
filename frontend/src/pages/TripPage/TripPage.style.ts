import { css } from '@emotion/react';

export const containerStyling = css({
  position: 'relative',

  width: '50vw',

  paddingBottom: '114px',

  '@media screen and (max-width: 1200px)': {
    width: '60vw',
  },

  '@media screen and (max-width: 600px)': {
    width: '100vw',
  },
});

export const mapContainerStyling = css({
  position: 'sticky',
  top: '81px',
  left: '50vw',

  width: '50vw',
  height: 'calc(100vh - 81px)',
});

export const skeletonContainerStyling = css({
  '& > div:first-of-type': {
    borderRadius: 0,
  },
});
