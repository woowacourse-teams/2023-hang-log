import { css } from '@emotion/react';

import { Theme } from '@styles/Theme';

export const radioContainerStyling = css({
  display: 'flex',
  flexDirection: 'column',
  gap: Theme.spacer.spacing2,
});

export const radioWrapperStyling = css({
  display: 'flex',
});

export const labelStyling = css({
  display: 'inline-flex',
  alignItems: 'center',
  marginRight: Theme.spacer.spacing2,

  cursor: 'pointer',
});

export const inputStyling = css({
  display: 'none',
  position: 'absolute',

  outline: '1px solid black',

  opacity: 0,
  pointerEvents: 'none',
});

export const buttonStyling = css({
  width: '16px',
  height: '16px',

  border: `2px solid ${Theme.color.gray200}`,
  borderRadius: '50%',
  boxSizing: 'border-box',

  marginRight: Theme.spacer.spacing2,

  'input[type="radio"]:checked + &': {
    border: `5px solid ${Theme.color.blue600}`,
  },
});
