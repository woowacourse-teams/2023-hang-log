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

export const deleteButtonStyling = css({
  marginTop: Theme.spacer.spacing3,
  padding: 0,

  color: Theme.color.gray600,
  fontWeight: 'normal',

  '&:hover': {
    backgroundColor: 'transparent !important',

    color: Theme.color.gray700,
  },

  '&:focus': {
    boxShadow: 'none',
  },
});

export const modalContentStyling = css({
  width: '350px',

  '& h6': {
    marginBottom: Theme.spacer.spacing3,

    color: Theme.color.red300,
  },
});

export const modalButtonContainerStyling = css({
  gap: Theme.spacer.spacing1,
  alignItems: 'stretch',

  width: '100%',
  marginTop: Theme.spacer.spacing5,

  '& > *': {
    width: '100%',
  },
});
