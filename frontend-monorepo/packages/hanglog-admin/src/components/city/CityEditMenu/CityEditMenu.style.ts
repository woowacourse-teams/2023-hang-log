import { css } from '@emotion/react';

import { Theme } from 'hang-log-design-system';

export const buttonStyling = css({
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

export const editIconStyling = css({
  '& path': {
    fill: Theme.color.gray600,
  },

  '&:hover path': {
    fill: Theme.color.gray800,
  },
});
