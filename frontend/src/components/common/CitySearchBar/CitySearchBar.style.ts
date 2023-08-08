import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export const containerStyling = css({
  width: '400px',
  margin: '0 auto',

  '@media screen and (max-width: 600px)': {
    width: '100%',
    margin: 0,
  },
});

export const wrapperStyling = css({
  display: 'flex',
  alignItems: 'center',
  flexWrap: 'nowrap',
  gap: '12px',

  padding: `0 ${Theme.spacer.spacing3}`,
  marginTop: Theme.spacer.spacing2,
  borderRadius: Theme.borderRadius.small,

  backgroundColor: Theme.color.gray100,

  transition: 'all .2s ease-in',

  '&:focus-within': {
    backgroundColor: Theme.color.white,
    boxShadow: Theme.boxShadow.shadow8,
  },
});

export const searchPinIconStyling = css({
  width: '20px',
  minWidth: '20px',
  height: '20px',
  maxHeight: '20px',
});

export const tagListStyling = css({
  display: '-webkit-box',
  gap: Theme.spacer.spacing1,
  alignItems: 'center',

  overflowX: 'scroll',
  whiteSpace: 'nowrap',
  '-ms-overflow-style': 'none',
  scrollbarWidth: 'none',

  '::-webkit-scrollbar': {
    display: 'none',
  },

  '& > div': {
    '& > *:focus-within': {
      boxShadow: 'none',
    },
  },

  '& > *': {
    '& > *': {
      paddingLeft: '0 !important',
    },
  },
});

export const inputStyling = css({
  width: 'fit-content',
});

export const badgeStyling = css({
  display: 'flex',
  alignItems: 'center',
  gap: Theme.spacer.spacing2,
});

export const closeIconStyling = css({
  width: '8px',
  height: '8px',

  cursor: 'pointer',

  path: {
    stroke: Theme.color.blue700,
    strokeWidth: '2px',
  },
});
