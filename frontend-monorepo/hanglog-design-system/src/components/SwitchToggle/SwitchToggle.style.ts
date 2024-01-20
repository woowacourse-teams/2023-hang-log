import { css } from '@emotion/react';

import { Theme } from '@styles/Theme';

export const switchToggleStyling = css({
  appearance: 'none',
  position: 'relative',
  width: '30px',
  height: '16px',

  boxSizing: 'content-box',
  backgroundColor: Theme.color.gray300,
  border: `4px solid ${Theme.color.gray300}`,
  borderColor: Theme.color.gray300,
  borderRadius: '16px',

  cursor: 'pointer',

  '&::before': {
    position: 'absolute',
    left: 0,
    width: '16px',
    height: '16px',

    backgroundColor: Theme.color.white,
    borderRadius: '50%',

    content: '""',
    transition: 'left 250ms linear',
  },

  '&:checked': {
    backgroundColor: Theme.color.blue600,
    border: `4px solid ${Theme.color.blue600}`,
  },

  '&:checked::before': {
    left: '14px',
  },
});
