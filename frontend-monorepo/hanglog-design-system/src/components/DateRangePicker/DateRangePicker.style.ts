import { css } from '@emotion/react';

import { Theme } from '@styles/Theme';

export const containerStyling = css({
  position: 'relative',
  display: 'inline-flex',
  gap: Theme.spacer.spacing3,
  alignItems: 'flex-start',

  padding: Theme.spacer.spacing3,

  '@media screen and (max-width: 632px)': {
    flexWrap: 'wrap',
  },

  '& > button': {
    position: 'absolute',
    top: '21px',

    backgroundColor: 'white',
    border: 'none',
    outline: 'white',

    cursor: 'pointer',

    '& svg': {
      width: '16px',
      height: '16px',

      '& path': {
        stroke: Theme.color.gray600,
      },

      '&:hover path': {
        stroke: Theme.color.black,
      },
    },
  },
});

export const previousButtonStyling = css({
  '@media screen and (max-width: 632px)': {
    left: Theme.spacer.spacing3,
  },
});

export const nextButtonStyling = css({
  right: Theme.spacer.spacing3,

  '@media screen and (max-width: 632px)': {
    left: '292px',
    right: 'unset',
  },
});
