import { css } from '@emotion/react';

import { Theme } from 'hang-log-design-system';

export const boxStyling = css({
  alignSelf: 'start',

  width: `calc((100vw - 196px) / 5)`,
  margin: 0,
  padding: 0,

  cursor: 'pointer',
  '@media screen and (max-width: 600px)': {
    width: `calc(100vw - 48px)`,
  },
});

export const imageBoxStyling = css({
  width: '100%',
  height: '176px',
});

export const imageStyling = css({
  objectFit: 'cover',

  width: '100%',
  height: '100%',
  minHeight: `calc(((100vw - 196px) / 24) * 3)`,
  maxHeight: `calc(((100vw - 196px) / 24) * 3)`,
  borderRadius: Theme.borderRadius.medium,

  backgroundColor: Theme.color.gray200,

  '@media screen and (max-width: 600px)': {
    minHeight: '256px',
    maxHeight: '256px',
  },
});

export const nameStyling = css({
  marginBottom: Theme.spacer.spacing1,

  fontWeight: '600',
});

export const badgeBoxStyling = css({
  width: `calc((100vw - 196px) / 5)`,
  minHeight: '24px',
  marginTop: Theme.spacer.spacing3,
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
    width: '100%',
  },
});

export const durationStyling = css({
  marginBottom: Theme.spacer.spacing1,
});

export const descriptionStyling = css({
  display: '-webkit-box',
  '-webkit-line-clamp': '2',
  '-webkit-box-orient': 'vertical',

  marginTop: '4px',
  width: '100%',

  overflow: 'hidden',
  textOverflow: 'ellipsis',
  wordBreak: 'break-all',
  whiteSpace: 'pre-wrap',
});

export const skeletonDurationTextStyling = css({
  marginTop: '2px',
});
