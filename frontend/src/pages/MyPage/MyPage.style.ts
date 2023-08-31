import { css } from '@emotion/react';

import { Theme } from 'hang-log-design-system';

export const containerStyling = css({
  display: 'flex',
  flexDirection: 'column',

  width: '360px',
  minHeight: 'calc(100vh - 81px)',
  margin: '0 auto',
  paddingTop: '72px',

  '@media screen and (max-width: 600px)': {
    width: `calc(100vw - ${Theme.spacer.spacing6})`,
    paddingTop: '56px',
  },
});

export const headingStyling = css({
  alignSelf: 'flex-start',

  marginBottom: Theme.spacer.spacing5,
});
