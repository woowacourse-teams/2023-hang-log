import { css } from '@emotion/react';

import { Theme } from 'hang-log-design-system';

export const imageStyling = css({
  minWidth: '32px',
  minHeight: '32px',
  maxWidth: '32px',
  maxHeight: '32px',
  width: '32px',
  height: '32px',
  border: 'none',
  outline: 0,
  borderRadius: '50%',

  backgroundColor: Theme.color.gray200,

  objectFit: 'cover',

  cursor: 'pointer',
});

export const menuListStyling = css({
  transform: 'translateY(36px)',

  '& > li': {
    padding: `${Theme.spacer.spacing2} ${Theme.spacer.spacing3}`,
  },
});
