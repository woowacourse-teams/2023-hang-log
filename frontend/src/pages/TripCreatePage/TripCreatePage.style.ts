import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export const containerStyling = css({
  width: '100%',
  flexDirection: 'column',
});

export const boxStyling = css({
  position: 'fixed',
  top: '45%',
  left: '50%',
  transform: 'translate(-45%, -50%)',
  gap: Theme.spacer.spacing5,

  zIndex: Theme.zIndex.overlayMiddle,
});

export const backgroundImage = css({
  position: 'fixed',
  bottom: Theme.spacer.spacing4,
  right: 0,
});
