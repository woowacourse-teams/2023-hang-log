import { css } from '@emotion/react';

import type { BadgeProps } from '@components/Badge/Badge';

import { Theme } from '@styles/Theme';

export const getVariantStyling = (variant: Required<BadgeProps>['variant']) => {
  const style = {
    default: css({
      backgroundColor: Theme.color.blue200,

      color: Theme.color.blue700,
    }),
    primary: css({
      backgroundColor: Theme.color.blue500,

      color: Theme.color.white,
    }),
    outline: css({
      backgroundColor: Theme.color.white,
      boxShadow: `inset 0 0 0 1px ${Theme.color.blue500}`,

      color: Theme.color.blue500,
    }),
  };

  return style[variant];
};

export const badgeStyling = css({
  padding: `4px 10px`,
  borderRadius: Theme.borderRadius.small,

  fontSize: Theme.text.xSmall.fontSize,
  lineHeight: Theme.text.xSmall.lineHeight,
});
