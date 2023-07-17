import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export const tripsBoxStyling = css({
  alignSelf: 'start',

  width: `calc((100vw - 196px) / 5)`,
  margin: 0,
  padding: 0,
});

export const tripsItemImageBoxStyling = css({
  width: '100%',
  height: '176px',
});

export const tripsItemImageStyling = css({
  objectFit: 'cover',

  width: '100%',
  height: '100%',
  minHeight: '176px',
  maxHeight: '176px',
  borderRadius: Theme.borderRadius.medium,
});

export const tripsItemNameStying = css({
  marginBottom: Theme.spacer.spacing1,

  fontWeight: '600',
});

export const tripsItemBadgeBoxStyling = css({
  width: `calc((100vw - 196px) / 5)`,
  minHeight: '22px',
  marginTop: Theme.spacer.spacing3,
  marginBottom: Theme.spacer.spacing2,

  overflowX: 'scroll',
  whiteSpace: 'nowrap',
  '-ms-overflow-style': 'none',
  scrollbarWidth: 'none',

  '& > span': {
    marginRight: Theme.spacer.spacing2,
  },

  '::-webkit-scrollbar': {
    display: 'none',
  },
});

export const tripsTiemDurationStyling = css({
  marginBottom: Theme.spacer.spacing1,
});

export const tripsDurationTextStyling = css({
  display: '-webkit-box',
  '-webkit-line-clamp': '2',
  '-webkit-box-orient': 'vertical',

  marginTop: '4px',
  width: '100%',

  textOverflow: 'ellipsis',
  overflow: 'hidden',
  wordBreak: 'break-word',
});
