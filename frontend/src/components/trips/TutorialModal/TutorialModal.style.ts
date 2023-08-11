import { css } from '@emotion/react';

import { Theme } from 'hang-log-design-system';

export const boxStyling = css({
  display: 'flex',
  flexDirection: 'column',
  alignItems: 'center',

  width: '450px',
  height: '500px',
  marginTop: '30px',

  '@media screen and (max-width: 600px)': {
    width: '346px',
    marginBottom: Theme.spacer.spacing6,
  },
});

export const modalStyling = css({
  '@media screen and (max-width: 600px)': {
    width: `calc(100vw - ${Theme.spacer.spacing5})`,
  },
});

export const carouselStyling = css({
  width: '385px',
  height: '412px',
});

export const buttonStyling = css({
  width: '100%',
});
