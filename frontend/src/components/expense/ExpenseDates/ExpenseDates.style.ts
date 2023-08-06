import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export const totalAmountContainerStyling = css({
  marginTop: Theme.spacer.spacing4,

  '& span': {
    fontWeight: 600,
  },

  '& p': {
    color: Theme.color.gray600,
  },
});
