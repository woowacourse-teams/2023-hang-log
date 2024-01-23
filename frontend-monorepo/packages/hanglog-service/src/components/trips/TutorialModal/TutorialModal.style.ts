import { css } from '@emotion/react';

import { Theme } from 'hang-log-design-system';

export const modalStyling = css({
  paddingTop: Theme.spacer.spacing5,

  '@media screen and (max-width: 600px)': {
    width: `calc(100vw - ${Theme.spacer.spacing5})`,
  },
});

export const buttonStyling = (isMobile: boolean) =>
  css({
    width: '100%',

    marginTop: isMobile ? 0 : 48,
  });

export const ItemStyling = (width: number, height: number) =>
  css({
    '& > svg': {
      width,
      height,
    },
  });
