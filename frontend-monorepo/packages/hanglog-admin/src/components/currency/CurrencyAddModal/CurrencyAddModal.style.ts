import { css } from '@emotion/react';

import { Theme } from 'hang-log-design-system';

export const wrapperStyling = css({
  width: '700px',
  minHeight: '528px',

  '@media screen and (max-width: 600px)': {
    width: `calc(100vw - ${Theme.spacer.spacing4})`,
    height: `80%`,
  },
});

export const formStyling = css({
  display: 'flex',
  flexDirection: 'column',
  gap: Theme.spacer.spacing4,
  width: '90%',
});

export const buttonStyling = css({
  width: '100%',
});

export const closeButtonStyling = css({
  position: 'absolute',
  right: Theme.spacer.spacing4,
  top: Theme.spacer.spacing4,
  alignSelf: 'flex-end',

  marginBottom: Theme.spacer.spacing1,

  border: 'none',
  backgroundColor: 'transparent',

  cursor: 'pointer',
});

export const closeIconStyling = css({
  width: '16px',
  height: '16px',
});
