import { css } from '@emotion/react';

import { Theme } from 'hang-log-design-system';

export const sectionStyling = css({
  position: 'relative',
  width: '100%',
  marginBottom: Theme.spacer.spacing5,
  padding: `${Theme.spacer.spacing4} 0`,

  '@media screen and (max-width: 600px)': {
    marginBottom: Theme.spacer.spacing3,
  },
});

export const titleStyling = css({
  marginTop: Theme.spacer.spacing3,
  marginBottom: Theme.spacer.spacing3,
});

export const badgeWrapperStyling = css({
  width: '60%',
  minHeight: '24px',
  marginBottom: Theme.spacer.spacing2,

  overflowX: 'scroll',
  whiteSpace: 'nowrap',
  '-ms-overflow-style': 'none',
  scrollbarWidth: 'none',

  '& > span': {
    marginRight: Theme.spacer.spacing1,
  },

  '::-webkit-scrollbar': {
    display: 'none',
  },

  '@media screen and (max-width: 600px)': {
    width: 'calc(100vw - 220px)',
  },
});

export const buttonWrapperStyling = css({
  position: 'absolute',
  top: Theme.spacer.spacing4,
  right: Theme.spacer.spacing4,
});
