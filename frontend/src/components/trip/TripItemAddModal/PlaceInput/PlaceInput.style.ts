import { css } from '@emotion/react';

import { Theme } from 'hang-log-design-system';

export const wrapperStyling = css({
  '& div': {
    padding: 0,
  },

  '& input': {
    padding: `12px ${Theme.spacer.spacing3}`,
  },
});
