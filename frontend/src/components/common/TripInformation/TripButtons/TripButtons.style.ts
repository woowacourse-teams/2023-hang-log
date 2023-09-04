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

export const svgButtonStyling = css({
  width: '20px',
  height: '20px',
  marginLeft: '12px',

  cursor: 'pointer',
});

export const binIconStyling = css({
  '& path': {
    stroke: Theme.color.white,
  },
});

export const editIconStyling = css({
  '& path': {
    fill: Theme.color.white,
  },
});
