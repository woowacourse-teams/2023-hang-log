import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export const wrapperStyling = css({
  overflow: 'initial',

  '@media screen and (max-width: 600px)': {
    width: `calc(100vw - ${Theme.spacer.spacing4})`,
  },
});

export const formStyling = css({
  display: 'flex',
  width: '400px',
  flexDirection: 'column',
  gap: Theme.spacer.spacing3,

  '> button': {
    width: '100%',
  },

  '@media screen and (max-width: 600px)': {
    width: `calc(100vw - ${Theme.spacer.spacing7})`,

    overflowY: 'auto',
    '-ms-overflow-style': 'none',
    scrollbarWidth: 'none',

    '&::-webkit-scrollbar': {
      '-webkit-appearance': 'none',
      width: 0,
      height: 0,
    },
  },
});

export const dateInputSupportingText = css({
  wordBreak: 'keep-all',
});

export const titleStyling = css({
  flexDirection: 'column',
  width: '400px',
  gap: '4px',

  '> div': {
    width: '100%',
  },
});

export const textareaStyling = css({
  resize: 'none',
  fontFamily: 'none',
});
