import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export const suggestionContainer = css({
  width: '500px',
  maxHeight: '300px',

  overflowY: 'auto',
  overflowX: 'hidden',

  transform: 'translateY(0)',
});

export const getSuggestionItemStyling = (isFocused: boolean) =>
  css({
    backgroundColor: isFocused ? Theme.color.gray200 : Theme.color.white,
  });

export const emptyTextStyling = css({
  margin: Theme.spacer.spacing2,

  color: Theme.color.gray500,
});
