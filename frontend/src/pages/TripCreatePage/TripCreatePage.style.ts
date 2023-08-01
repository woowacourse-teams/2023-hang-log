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

  zIndex: Theme.zIndex.overlayMiddle,
});

export const backgroundImage = css({
  position: 'absolute',
  bottom: Theme.spacer.spacing4,
  right: 0,
});
