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

export const getEditMenuStyling = (hasImage: boolean, imageHeight: number) => {
  return css({
    position: 'absolute',
    top: 0,
    right: 0,

    cursor: 'pointer',

    '@media screen and (max-width: 600px)': {
      top: hasImage ? `calc(${imageHeight}px + ${Theme.spacer.spacing3})` : 0,
    },

    '& svg': {
      width: '18px',
      height: '18px',
    },
  });
};

export const binIconStyling = css({
  '& path': {
    stroke: Theme.color.gray600,
  },

  '&:hover path': {
    stroke: Theme.color.red300,
  },
});

export const editIconStyling = css({
  '& path': {
    fill: Theme.color.gray600,
  },

  '&:hover path': {
    fill: Theme.color.blue600,
  },
});

export const modalContentStyling = css({
  width: '350px',

  '& h6': {
    marginBottom: Theme.spacer.spacing3,

    color: Theme.color.red300,
  },
});

export const modalButtonContainerStyling = css({
  gap: Theme.spacer.spacing1,
  alignItems: 'stretch',

  width: '100%',
  marginTop: Theme.spacer.spacing5,

  '& > *': {
    width: '100%',
  },
});
