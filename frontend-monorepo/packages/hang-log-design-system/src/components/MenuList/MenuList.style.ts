import { css } from '@emotion/react';

import { Theme } from '@styles/Theme';

export const menuListStyling = css({
  position: 'absolute',
  transform: 'translateY(49px)',
  display: 'flex',
  flexDirection: 'column',
  zIndex: Theme.zIndex.overlayMiddle,

  minWidth: '150px',

  padding: `${Theme.spacer.spacing2} 0`,
  marginTop: Theme.spacer.spacing2,

  backgroundColor: Theme.color.white,
  borderRadius: Theme.borderRadius.small,

  boxShadow: Theme.boxShadow.shadow8,

  transition: 'all .2 ease-in',
});
