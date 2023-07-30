import { css } from '@emotion/react';

export const containerStyling = css({
  position: 'relative',
  width: '50vw',

  '@media screen and (max-width: 1200px)': {
    width: '60vw',
  },
});

export const mapContainerStyling = css({
  position: 'sticky',
  top: '81px',
  left: '50vw',

  width: '50vw',
  height: 'calc(100vh - 81px)',
});

export const addButtonStyling = css({
  position: 'fixed',
  left: 'calc(50vw - 114px)',
  bottom: '50px',

  '@media screen and (max-width: 1200px)': {
    left: 'calc(60vw - 114px)',
  },
});

export const skeletonContainerStyling = css({
  '& > div:first-of-type': {
    borderRadius: 0,
  },
});
