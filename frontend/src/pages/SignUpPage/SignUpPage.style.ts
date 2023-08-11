import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export const containerStyling = css({
  position: 'relative',
  height: 'calc(100vh - 81px)',
  padding: Theme.spacer.spacing4,

  '@media screen and (max-width: 600px)': {
    height: 'calc(100vh - 65px)',
    padding: ` 56px ${Theme.spacer.spacing4} 0 ${Theme.spacer.spacing4}`,
  },
});

export const headingStyling = css({
  marginTop: Theme.spacer.spacing5,
});

export const buttonContainerStyling = css({
  width: '350px',
  maxWidth: '100%',
  marginTop: Theme.spacer.spacing6,
});

export const backgroundImageStyling = css({
  position: 'absolute',
  bottom: Theme.spacer.spacing4,
  left: 0,
  zIndex: -1,

  '@media screen and (max-width: 600px)': {
    display: 'none',
  },
});
