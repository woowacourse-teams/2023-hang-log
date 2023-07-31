import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export const containerStyling = css({
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

  '& *': {
    color: Theme.color.gray600,
  },
});
