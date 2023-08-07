import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export const containerStyling = css({
  flexDirection: 'column',
  alignItems: 'center',

  width: '100%',
  height: 'calc(100vh - 81px)',
});

export const boxStyling = css({
  position: 'relative',
  gap: Theme.spacer.spacing5,

  paddingTop: '72px',

  '@media (max-width: 600px)': {
    width: '85%',
  },
});

export const headingStyling = css({
  '@media (max-width: 600px)': {
    fontSize: '24px',
  },
});

export const backgroundImage = css({
  position: 'absolute',
  bottom: Theme.spacer.spacing4,
  right: 0,
  zIndex: -1,

  '@media (max-width: 600px)': {
    display: 'none',
  },
});
