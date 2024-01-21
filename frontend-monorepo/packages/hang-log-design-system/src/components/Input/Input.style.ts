import { css } from '@emotion/react';

import type { InputProps } from '@components/Input/Input';

import { Theme } from '@styles/Theme';

export const inputContainerStyling = css({
  display: 'flex',
  flexDirection: 'column',
  gap: Theme.spacer.spacing2,
});

export const inputWrapperStyling = (isError: Required<InputProps>['isError']) => {
  return css({
    display: 'flex',
    gap: '12px',
    alignItems: 'center',

    paddingTop: 0,
    paddingBottom: 0,
    borderRadius: Theme.borderRadius.small,

    backgroundColor: isError ? `${Theme.color.red100} !important` : 'transparent',

    transition: 'all .2s ease-in',

    '&:focus-within': {
      backgroundColor: isError ? Theme.color.red100 : Theme.color.white,
      boxShadow: isError
        ? `inset 0 0 0 1px ${Theme.color.red200}`
        : `inset 0 0 0 1px ${Theme.color.gray300}`,
    },

    '& svg': {
      width: '16px',
      height: '16px',
    },
  });
};

export const getVariantStyling = (variant: Required<InputProps>['variant']) => {
  const style = {
    default: css({
      backgroundColor: Theme.color.gray100,
    }),

    text: css({
      backgroundColor: 'transparent',
    }),
  };

  return style[variant];
};

export const getSizeStyling = (size: Required<InputProps>['size']) => {
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

export const getInputStyling = css({
  width: '100%',
  paddingLeft: 0,
  paddingRight: 0,
  border: 'none',
  borderRadius: Theme.borderRadius.small,
  outline: 0,

  backgroundColor: 'transparent',
});
