import { css } from '@emotion/react';

import { Theme } from 'hang-log-design-system';

export const imageStyling = css({
  width: '854px',
  marginLeft: Theme.spacer.spacing6,

  '@media screen and (max-width: 1200px)': {
    width: '65%',
  },

  '@media screen and (max-width: 600px)': {
    display: 'none',
  },
});

export const headingStyling = css({
  margin: `0 ${Theme.spacer.spacing9}`,

  fontWeight: 400,

  '@media screen and (max-width: 600px)': {
    padding: '36px 0',
    fontSize: Theme.heading.medium.fontSize,
  },
});
