import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export const tripsImageBoxStyling = css({
  width: `calc((100vw - 196px) / 5)`,
  height: '304px',
  maxHeight: '304px',
  margin: '0',
  padding: '0',
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
  fontWeight: '600',
  marginBottom: Theme.spacer.spacing1,
});

export const tripsItemBadgeBoxStyling = css({
  width: `calc((100vw - 196px) / 5)`,
  whiteSpace: 'nowrap',
  marginTop: Theme.spacer.spacing3,
  marginBottom: Theme.spacer.spacing2,
  minHeight: '22px',
  overflowX: 'scroll',
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
