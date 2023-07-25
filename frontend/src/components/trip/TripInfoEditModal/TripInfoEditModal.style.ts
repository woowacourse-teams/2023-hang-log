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

export const cityInputErrorTextStyling = css({
  lineHeight: 0,
  color: Theme.color.red200,
});

export const dateInputTextStyling = css({
  maxWidth: '400px',
});
