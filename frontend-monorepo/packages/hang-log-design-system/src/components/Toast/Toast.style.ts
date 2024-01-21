import { css } from '@emotion/react';

import type { ToastProps } from '@components/Toast/Toast';

import { Theme } from '@styles/Theme';
import { fadeIn, fadeOut, moveUp } from '@styles/animation';

export const getVariantStyling = (variant: Required<ToastProps>['variant']) => {
  const style = {
    default: css({
      backgroundColor: Theme.color.blue600,
    }),
    success: css({
      backgroundColor: Theme.color.green,
    }),
    error: css({
      backgroundColor: Theme.color.red300,
    }),
  };

  return style[variant];
};

export const getToastStyling = (isVisible: boolean) => {
  return css({
    bottom: Theme.spacer.spacing6,
    display: 'flex',
    justifyContent: 'space-between',
    gap: Theme.spacer.spacing4,
    alignItems: 'center',

    minWidth: '300px',
    padding: `14px ${Theme.spacer.spacing3}`,
    borderRadius: Theme.borderRadius.small,

    boxShadow: Theme.boxShadow.shadow9,

    color: Theme.color.white,

    animation: isVisible
      ? `${fadeIn} 0.2s ease-in, ${moveUp} 0.2s ease-in`
      : `${fadeOut} 0.2s ease-in forwards`,

    '& > svg': {
      width: '16px',
      height: '16px',

      '& path': {
        stroke: Theme.color.white,
      },
    },
  });
};

export const contentStyling = css({
  display: 'flex',
  gap: Theme.spacer.spacing2,
  alignItems: 'center',

  fontSize: Theme.text.medium.fontSize,
  lineHeight: Theme.text.medium.lineHeight,
});

export const closeIconStyling = css({
  cursor: 'pointer',
});
