import { css } from '@emotion/react';

import type { FloatingButtonProps } from '@components/FloatingButton/FloatingButton';

import { Theme } from '@styles/Theme';

export const getSizeStyling = (size: Required<FloatingButtonProps>['size']) => {
  const style = {
    medium: css({
      width: '64px',
      height: '64px',
    }),

    small: css({
      width: '32px',
      height: '32px',
    }),
  };

  return style[size];
};

export const getIconSizeStyling = (size: Required<FloatingButtonProps>['size']) => {
  const style = {
    medium: css({
      width: '24px',
      height: '24px',
    }),
    small: css({
      width: '16px',
      height: '16px',

      path: {
        strokeWidth: '1',
      },
    }),
  };

  return style[size];
};

export const getIconVariantStyling = (variant: Required<FloatingButtonProps>['variant']) => {
  const style = {
    primary: css({
      path: {
        stroke: Theme.color.white,
      },
    }),
    default: css({
      path: {
        stroke: Theme.color.black,
      },
    }),
  };

  return style[variant];
};

export const floatingButtonStyling = css({
  display: 'flex',
  justifyContent: 'center',
  alignItems: 'center',

  border: 'none',
  borderRadius: '50%',
  outline: `0 solid ${Theme.color.white}`,

  boxShadow: Theme.boxShadow.shadow5,

  transition: 'all .2s ease-in',

  cursor: 'pointer',

  '&:focus': {
    outlineWidth: '1px',
  },
});
