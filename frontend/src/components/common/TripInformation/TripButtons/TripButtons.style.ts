import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export const moreButtonStyling = css({
  border: 'none',
  outline: 0,

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

export const moreMenuStyling = css({
  display: 'flex',
  flexDirection: 'column',
  justifyContent: 'center',

  marginLeft: Theme.spacer.spacing3,
});

export const moreMenuListStyling = css({
  transform: 'translateY(64px)',

  '& > li': {
    padding: `${Theme.spacer.spacing2} ${Theme.spacer.spacing3}`,

    color: Theme.color.gray800,
  },
});
