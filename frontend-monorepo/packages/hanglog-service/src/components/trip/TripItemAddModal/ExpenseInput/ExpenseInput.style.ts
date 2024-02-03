import { css } from '@emotion/react';

import { Theme } from 'hang-log-design-system';

export const leftContainerStyling = css({
  '& > div > div': {
    border: 'none',
  },
});

export const categorySelectStyling = css({
  width: '74px',
  height: '48px',
  paddingRight: Theme.spacer.spacing1,
  border: 'none',
});

export const currencySelectStyling = css({
  width: '64px',
  height: '48px',
  paddingRight: Theme.spacer.spacing1,
  border: 'none',
});
