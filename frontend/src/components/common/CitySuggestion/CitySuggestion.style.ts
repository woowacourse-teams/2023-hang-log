import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export const containerStyling = css({
  width: '400px',
  maxHeight: '300px',

  overflowY: 'auto',
  overflowX: 'hidden',

  transform: 'translateY(0)',

  '@media screen and (max-width: 600px)': {
    width: 'calc(100% - 48px)',
  },
});

export const getItemStyling = (isFocused: boolean) => {
  return css({
    backgroundColor: isFocused ? Theme.color.gray200 : Theme.color.white,
    fontSize: Theme.text.medium.fontSize,
    padding: `12px ${Theme.spacer.spacing3}`,
  });
};

export const emptyTextStyling = css({
  padding: `12px ${Theme.spacer.spacing3}`,

  color: Theme.color.gray500,
});
