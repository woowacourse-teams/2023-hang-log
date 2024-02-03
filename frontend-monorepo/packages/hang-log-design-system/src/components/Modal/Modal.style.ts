import { css } from '@emotion/react';

import { Theme } from '@styles/Theme';
import { fadeIn } from '@styles/animation';

export const backdropStyling = css({
  position: 'fixed',
  top: 0,
  left: 0,
  zIndex: Theme.zIndex.overlayMiddle,

  width: '100%',
  height: '100%',

  backgroundColor: 'rgba(0, 0, 0, .15)',

  cursor: 'pointer',
});

export const dialogStyling = css({
  position: 'fixed',
  top: '50%',
  transform: 'translateY(-50%)',
  zIndex: Theme.zIndex.overlayTop,
  display: 'flex',
  flexDirection: 'column',
  justifyContent: 'center',
  alignItems: 'center',

  minWidth: '300px',
  padding: Theme.spacer.spacing4,
  margin: '0 auto',

  border: 'none',
  borderRadius: Theme.borderRadius.large,

  backgroundColor: Theme.color.white,
  boxShadow: Theme.boxShadow.shadow8,

  animation: `${fadeIn} 0.2s ease-in`,
});

export const closeButtonStyling = css({
  position: 'absolute',
  right: '24px',
  top: '24px',
  alignSelf: 'flex-end',

  marginBottom: Theme.spacer.spacing1,

  border: 'none',
  backgroundColor: 'transparent',

  cursor: 'pointer',
});

export const closeIconStyling = css({
  width: '16px',
  height: '16px',
});
