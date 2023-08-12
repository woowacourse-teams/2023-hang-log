import { css } from '@emotion/react';

import { Theme } from 'hang-log-design-system';

export const formStyling = css({
  width: '100%',
  display: 'flex',
  flexDirection: 'column',
  gap: Theme.spacer.spacing4,
  marginTop: Theme.spacer.spacing5,

  '> button': {
    width: '100%',
  },

  '@media screen and (max-width: 600px)': {
    minHeight: 'calc(100vh - 124px)',
    marginTop: Theme.spacer.spacing4,

    '> button': {
      position: 'absolute',
      width: 'calc(100% - 48px)',
      bottom: '100px',
    },
  },
});
