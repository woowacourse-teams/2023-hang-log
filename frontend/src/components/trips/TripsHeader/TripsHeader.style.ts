import { css } from '@emotion/react';

import { Theme } from 'hang-log-design-system';

export const containerStyling = css({
  width: '100%',
  marginTop: Theme.spacer.spacing3,
  marginBottom: Theme.spacer.spacing4,

  justifyContent: 'space-between',
  alignItems: 'center',

  '@media screen and (max-width: 600px)': {
    justifyContent: 'center',
  },
});

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
  wordBreak: 'keep-all',

  '@media screen and (max-width: 600px)': {
    margin: `0 ${Theme.spacer.spacing5}`,
    padding: '48px 0 16px 0',
    fontSize: Theme.heading.medium.fontSize,
  },
});
