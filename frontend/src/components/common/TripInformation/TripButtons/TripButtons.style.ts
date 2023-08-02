import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export const iconButtonStyling = css({
  marginLeft: Theme.spacer.spacing3,
  border: 'none',

  backgroundColor: 'transparent',

  cursor: 'pointer',

  '& svg': {
    width: '20px',
    height: '20px',

    '& path': {
      stroke: Theme.color.white,
      strokeWidth: 1.5,
    },
  },
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
  transform: 'translateY(36px)',

  '& > li': {
    padding: `${Theme.spacer.spacing2} ${Theme.spacer.spacing3}`,

    color: Theme.color.gray800,
  },
});
