import { css } from '@emotion/react';

export const checkboxStyling = css({
  display: 'flex',
  gap: '12px',
  alignItems: 'center',

  '& > svg': {
    width: '28px',
    height: '28px',
  },
});

export const inputStyling = css({
  display: 'none',
  padding: 0,
});
