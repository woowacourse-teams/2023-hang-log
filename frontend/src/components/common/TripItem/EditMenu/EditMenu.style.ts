import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

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
  minWidth: 'unset',
  width: 'max-content',

  transform: 'translateY(16px)',

  '& > li': {
    padding: `${Theme.spacer.spacing2} ${Theme.spacer.spacing3}`,
  },
});
