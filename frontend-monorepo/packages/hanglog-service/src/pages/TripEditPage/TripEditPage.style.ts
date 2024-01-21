import { css } from '@emotion/react';

import { Theme } from 'hang-log-design-system';

export const containerStyling = css({
  position: 'relative',
  width: '50vw',

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

  '@media screen and (max-width: 600px)': {
    height: 'calc(100vh - 65px)',
  },
});

export const addButtonStyling = css({
  position: 'sticky',
  left: 'calc(50vw - 114px)',
  bottom: '50px',

  marginBottom: '50px',

  '@media screen and (max-width: 1200px)': {
    left: 'calc(60vw - 114px)',
  },

  '@media screen and (max-width: 600px)': {
    bottom: Theme.spacer.spacing4,
    left: 'calc(100vw - 88px)',
  },
});

export const skeletonContainerStyling = css({
  '& > div:first-of-type': {
    borderRadius: 0,
  },
});
