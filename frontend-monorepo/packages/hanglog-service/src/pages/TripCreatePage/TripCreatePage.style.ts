import { css } from '@emotion/react';

import { Theme } from 'hang-log-design-system';

export const containerStyling = css({
  flexDirection: 'column',
  alignItems: 'center',

  width: '100%',
  height: 'calc(100vh - 81px)',

  '@media screen and (max-width: 600px)': {
    height: 'calc(100vh - 65px)',
  },
});

export const boxStyling = css({
  position: 'relative',
  width: '400px',
  gap: Theme.spacer.spacing5,

  paddingTop: '72px',

  '@media screen and (max-width: 600px)': {
    width: '100%',
    padding: ` 56px ${Theme.spacer.spacing4} 0 ${Theme.spacer.spacing4}`,
  },
});

export const backgroundImageStyling = css({
  position: 'absolute',
  bottom: Theme.spacer.spacing4,
  right: 0,
  zIndex: -1,

  '@media screen and (max-width: 600px)': {
    display: 'none',
  },
});
