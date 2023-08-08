import { css } from '@emotion/react';

export const boxStyling = css({
  display: 'flex',
  flexDirection: 'column',
  alignItems: 'center',

  width: '450px',
  height: '581px',
  marginTop: '30px',
  '@media screen and (max-width: 600px)': {
    width: '346px',
    height: '493px',
  },
});

export const modalStyling = css({
  '@media screen and (max-width: 600px)': {
    padding: '18px',
  },
});

export const carouselStyling = css({
  width: '385px',
  height: '412px',
});

export const buttonStyling = css({
  width: '100%',
  marginTop: 'auto',
});
