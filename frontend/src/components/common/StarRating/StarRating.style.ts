import { css } from '@emotion/react';

export const getContainerStyling = (size: number, gap: number) => {
  return css({
    width: 'initial',
    height: 'initial',
    margin: 0,
    padding: 0,

    '& svg': {
      width: size,
      height: size,
    },
  });
};
