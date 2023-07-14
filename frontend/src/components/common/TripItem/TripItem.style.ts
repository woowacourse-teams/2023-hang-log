import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export const containerStyling = css({
  position: 'relative',

  minHeight: '120px',
});

export const informationContainerStyling = css({
  width: '100%',
});

export const subInformationStyling = css({
  marginTop: '2px',

  color: Theme.color.gray600,
});

export const starRatingStyling = css({
  marginTop: Theme.spacer.spacing2,
  marginBottom: Theme.spacer.spacing3,
});

export const memoStyling = css({
  marginBottom: Theme.spacer.spacing3,
});

export const moreButtonStyling = css({
  position: 'absolute',

  width: '20px',
  height: '20px',
  border: 'none',
  outline: 0,

  backgroundColor: 'transparent',

  cursor: 'pointer',

  '& svg': {
    width: '20px',
    height: '20px',
  },
});

export const moreMenuStyling = css({
  position: 'absolute',
  top: 0,
  right: 0,
});

export const moreMenuListStyling = css({
  transform: 'translateY(16px)',
});
