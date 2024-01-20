import { css } from '@emotion/react';

export const clickableLikeStyling = css({
  position: 'absolute',

  transform: 'translateX(calc((100vw - 356px) / 5)) translateY(12px)',

  '@media screen and (max-width: 600px)': {
    transform: 'translateX(calc(100vw - 80px)) translateY(12px)',
  },
});
