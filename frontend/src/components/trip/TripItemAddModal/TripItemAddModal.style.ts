import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export const wrapperStyling = css({
  width: '696px',
  height: '528px',
});

export const formStyling = css({
  display: 'flex',
  flexDirection: 'column',
  gap: Theme.spacer.spacing4,

  '& > button': {
    width: '100%',
  },
});
