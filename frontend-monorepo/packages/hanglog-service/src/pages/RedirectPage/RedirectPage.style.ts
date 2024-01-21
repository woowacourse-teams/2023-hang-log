import { css } from '@emotion/react';

export const containerStyling = css({
  minHeight: 'calc(100vh - 81px)',

  '@media screen and (max-width: 600px)': {
    minHeight: 'calc(100vh - 65px)',
  },
});
