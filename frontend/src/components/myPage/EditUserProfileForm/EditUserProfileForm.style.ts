import { css } from '@emotion/react';

import { Theme } from 'hang-log-design-system';

export const formStyling = css({
  display: 'flex',
  flexDirection: 'column',
  alignItems: 'center',
  gap: Theme.spacer.spacing1,

  width: '360px',

  '@media screen and (max-width: 600px)': {
    width: `calc(100vw - ${Theme.spacer.spacing6})`,
  },
});

export const imageInputStyling = css({
  marginBottom: Theme.spacer.spacing5,
});

export const buttonStyling = css({
  width: '100%',
  marginTop: Theme.spacer.spacing3,
});

export const linkStyling = css({
  marginTop: Theme.spacer.spacing3,

  color: Theme.color.gray600,

  '&:hover': {
    color: Theme.color.gray800,
  },
});
