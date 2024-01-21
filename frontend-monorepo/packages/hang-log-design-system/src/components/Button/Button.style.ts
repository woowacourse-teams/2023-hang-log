import { css } from '@emotion/react';

import { Theme } from '@styles/Theme';

import type { ButtonProps } from './Button';

export const getVariantStyling = (variant: Required<ButtonProps>['variant']) => {
  const style = {
    primary: css({
      backgroundColor: Theme.color.blue500,

      color: Theme.color.white,

      '&:hover:enabled': {
        backgroundColor: Theme.color.blue600,
      },

      '&:focus': {
        boxShadow: `0 0 0 3px ${Theme.color.blue600}`,
      },
    }),
    secondary: css({
      backgroundColor: Theme.color.blue100,

      color: Theme.color.blue600,

      '&:hover:enabled': {
        backgroundColor: Theme.color.blue200,
      },

      '&:focus': {
        boxShadow: `0 0 0 3px ${Theme.color.blue200}`,
      },
    }),
    default: css({
      backgroundColor: Theme.color.gray100,

      color: Theme.color.gray800,

      '&:hover:enabled': {
        backgroundColor: `${Theme.color.gray200}`,
      },

      '&:focus': {
        boxShadow: `0 0 0 3px ${Theme.color.gray200}`,
      },
    }),
    outline: css({
      backgroundColor: Theme.color.white,

      color: Theme.color.gray800,
      boxShadow: `inset 0 0 0 1px ${Theme.color.gray200}`,

      '&:hover:enabled': {
        backgroundColor: Theme.color.gray200,
      },

      '&:focus': {
        boxShadow: `0 0 0 3px ${Theme.color.gray300}`,
      },
    }),

    text: css({
      backgroundColor: Theme.color.white,

      color: Theme.color.gray800,

      '&:hover:enabled': {
        backgroundColor: Theme.color.gray100,
      },

      '&:focus': {
        boxShadow: `0 0 0 3px ${Theme.color.gray100}`,
      },
    }),
    danger: css({
      backgroundColor: Theme.color.red200,

      color: Theme.color.white,

      '&:hover:enabled': {
        backgroundColor: Theme.color.red300,
      },

      '&:focus': {
        boxShadow: `0 0 0 3px ${Theme.color.red300}`,
      },
    }),
  };

  return style[variant];
};

export const getSizeStyling = (size: Required<ButtonProps>['size']) => {
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

export const buttonStyling = css({
  display: 'flex',
  justifyContent: 'center',
  alignItems: 'center',

  border: 'none',
  borderRadius: `${Theme.borderRadius.small}`,
  outline: `0 solid ${Theme.color.white}`,

  backgroundColor: Theme.color.white,

  fontWeight: 600,

  transition: 'all .2s ease-in',

  cursor: 'pointer',

  '&:focus': {
    outlineWidth: '1px',
  },

  '&:disabled': {
    opacity: '.4',
  },
});
