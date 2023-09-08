import { css } from '@emotion/react';

import { Theme } from 'hang-log-design-system';

export const containerStyling = css({
  position: 'sticky',
  display: 'flex',
  flexDirection: 'column',
  justifyContent: 'center',
  alignItems: 'center',
  gap: Theme.spacer.spacing1,

  width: '100vw',
  height: '164px',
  padding: '0 64px',
  borderTop: `1px solid ${Theme.color.gray200}`,

  backgroundColor: Theme.color.white,

  '@media screen and (max-width: 600px)': {
    height: '124px',
  },

  '& *': {
    color: Theme.color.gray600,
  },
});
