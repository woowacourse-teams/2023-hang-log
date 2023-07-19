import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export const containerStyling = css({
  width: '100%',
  flexDirection: 'column',
  alignItems: 'center',
  justifyContent: 'center',
});

export const boxStyling = css({
  position: 'relative',
  top: '150px',
  gap: Theme.spacer.spacing5,

  backgroundColor: Theme.color.white,
  zIndex: Theme.zIndex.overlayMiddle,
});

export const backgroundImage = css({
  position: 'fixed',
  bottom: Theme.spacer.spacing4,
  right: 0,
});
