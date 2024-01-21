import { css } from '@emotion/react';

export const inputContainerStyling = (size: number, gap: number) => {
  return css({
    display: 'flex',

    'div:not(:last-of-type):nth-of-type(even)': {
      paddingRight: gap,
    },
    svg: {
      width: size / 2,
      height: size,
    },
  });
};

export const starItemStyling = css({
  cursor: 'pointer',
});
