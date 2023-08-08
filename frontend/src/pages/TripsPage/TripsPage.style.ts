import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export const addButtonStyling = css({
  position: 'fixed',
  right: '50px',
  bottom: '50px',

  '@media screen and (max-width: 600px)': {
    right: Theme.spacer.spacing4,
    bottom: Theme.spacer.spacing4,
  },
});
