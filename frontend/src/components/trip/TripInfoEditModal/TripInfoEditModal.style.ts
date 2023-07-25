import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export const formStyling = css({
  display: 'flex',
  flexDirection: 'column',
  gap: Theme.spacer.spacing3,

  '> button': {
    width: '400px',
  },
});

export const supportingTextStyling = css({
  maxWidth: '400px',
});
