import { css } from '@emotion/react';

import { Theme } from 'hang-log-design-system';

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
