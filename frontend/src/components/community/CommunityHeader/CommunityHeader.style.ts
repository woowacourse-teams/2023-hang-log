import { css } from '@emotion/react';

import { Theme } from 'hang-log-design-system';

export const imageStyling = css({
  width: '854px',
  marginRight: '50px',

  '@media screen and (max-width: 1200px)': {
    width: '65%',
  },

  '@media screen and (max-width: 600px)': {
    display: 'none',
  },
});

export const headingStyling = css({
  marginLeft: '50px',
  fontSize: Theme.heading.xLarge.fontSize,

  '@media screen and (max-width: 600px)': {
    marginLeft: Theme.spacer.spacing4,
    padding: '36px 0',
    fontSize: Theme.heading.medium.fontSize,
  },
});

export const headingEmphasisStyling = css({
  color: Theme.color.blue500,
});
