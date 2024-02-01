import { css } from '@emotion/react';

import { Theme } from 'hang-log-design-system';

export const ContainerStyling = css({
  padding: `${Theme.spacer.spacing6} 0px ${Theme.spacer.spacing6} 0px`,

  '&>first-of-type': {
    paddingRight: Theme.spacer.spacing4,
  },

  '&>last-of-type': {
    paddingLeft: Theme.spacer.spacing4,
  },
});

export const BoxStyling = css({
  display: 'flex',
  justifyContent: 'center',
  alignItems: 'center',

  width: Theme.spacer.spacing5,
  height: Theme.spacer.spacing5,

  borderRadius: Theme.borderRadius.small,

  transition: 'all .1s ease-in',
  cursor: 'pointer',

  ':hover': {
    backgroundColor: '#dddddd',
  },
});

export const SelectedBoxStyling = css({
  backgroundColor: Theme.color.blue500,
  color: Theme.color.white,

  ':hover': {
    backgroundColor: Theme.color.blue600,
  },
});

export const LeftButtonStyling = css({
  display: 'flex',
  alignItems: 'center',

  paddingRight: Theme.spacer.spacing4,

  cursor: 'pointer',
});

export const RightButtonStyling = css({
  display: 'flex',
  alignItems: 'center',

  paddingLeft: Theme.spacer.spacing4,

  cursor: 'pointer',
});
