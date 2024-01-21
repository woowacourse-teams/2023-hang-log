import { css } from '@emotion/react';

import { Theme } from '@styles/Theme';

export const menuItemStyling = css({
  padding: Theme.spacer.spacing2,

  fontSize: Theme.text.small.fontSize,
  lineHeight: Theme.text.small.lineHeight,

  transition: 'all .2s ease-in',

  cursor: 'pointer',

  '&:hover': {
    backgroundColor: Theme.color.gray100,
  },
});
