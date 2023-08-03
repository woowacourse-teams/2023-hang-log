import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export const containerStyling = css({
  width: '100%',

  '& p': {
    color: Theme.color.gray600,
  },
});

export const amountStyling = css({
  fontWeight: 600,
});
