import { css } from '@emotion/react';

export const addButtonStyling = css({
  position: 'fixed',
  right: '50px',
  bottom: '50px',
  '@media screen and (max-width: 600px)': {
    right: '28px',
    bottom: '28px',
  },
});
