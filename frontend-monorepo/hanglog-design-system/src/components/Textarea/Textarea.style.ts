import { css } from '@emotion/react';

import type { TextareaProps } from '@components/Textarea/Textarea';

import { Theme } from '@styles/Theme';

export const textareaContainerStyling = css({
  display: 'flex',
  flexDirection: 'column',
  gap: Theme.spacer.spacing2,
});

export const getSizeStyling = (size: Required<TextareaProps>['size']) => {
  const style = {
    large: css({
      padding: '14px 16px',

      fontSize: Theme.text.medium.fontSize,
      lineHeight: Theme.text.medium.lineHeight,
    }),

    medium: css({
      padding: '12px 16px',

      fontSize: Theme.text.medium.fontSize,
      lineHeight: Theme.text.medium.lineHeight,
    }),

    small: css({
      padding: '8px 12px',

      fontSize: Theme.text.small.fontSize,
      lineHeight: Theme.text.small.lineHeight,
    }),
  };

  return style[size];
};

export const getTextareaStyling = (isError: Required<TextareaProps>['isError']) => {
  return css({
    width: '100%',
    padding: 0,
    border: 'none',
    borderRadius: Theme.borderRadius.small,
    outline: 0,

    backgroundColor: isError ? `${Theme.color.red100}` : Theme.color.gray100,

    transition: 'all .2s ease-in',

    '&:focus-within': {
      backgroundColor: isError ? Theme.color.red100 : Theme.color.white,
      boxShadow: isError ? `0 0 0 1px ${Theme.color.red200}` : `0 0 0 1px ${Theme.color.gray300}`,
    },
  });
};
