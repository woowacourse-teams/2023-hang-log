import { css } from '@emotion/react';

import { Theme } from 'hang-log-design-system';

export const inputStyling = css({
  padding: `${Theme.spacer.spacing2} 0`,
  paddingTop: 0,
  border: 'none',
  borderBottom: `2px solid transparent`,
  outline: 0,

  maxWidth: '80%',
  minWidth: '60%',

  fontSize: Theme.heading.xSmall.fontSize,
  lineHeight: Theme.heading.xSmall.lineHeight,
  fontWeight: 'bold',
  textOverflow: 'ellipsis',

  transition: 'all .2s ease-in',

  '@media screen and (max-width: 600px)': {
    maxWidth: '60%',
    minWidth: '40%',
  },

  '&:focus': {
    boxShadow: `inset`,
    borderColor: Theme.color.gray200,
  },
});
