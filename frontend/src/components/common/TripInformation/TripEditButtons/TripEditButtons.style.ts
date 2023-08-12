import { css } from '@emotion/react';

import { Theme } from 'hang-log-design-system';

export const editButtonStyling = css({
  backgroundColor: 'transparent',

  color: Theme.color.white,

  '&:hover:enabled': {
    backgroundColor: 'rgba(255, 255, 255, .2)',
  },
});
