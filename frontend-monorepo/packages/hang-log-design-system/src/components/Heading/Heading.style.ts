import { css } from '@emotion/react';

import type { HeadingProps } from '@components/Heading/Heading';

import { Theme } from '@styles/Theme';

export const getSizeStyling = (size: Required<HeadingProps>['size']) => {
  const style = {
    xxLarge: css({
      fontSize: Theme.heading.xxLarge.fontSize,
      lineHeight: Theme.heading.xxLarge.lineHeight,
    }),
    xLarge: css({
      fontSize: Theme.heading.xLarge.fontSize,
      lineHeight: Theme.heading.xLarge.lineHeight,
    }),
    large: css({
      fontSize: Theme.heading.large.fontSize,
      lineHeight: Theme.heading.large.lineHeight,
    }),
    medium: css({
      fontSize: Theme.heading.medium.fontSize,
      lineHeight: Theme.heading.medium.lineHeight,
    }),
    small: css({
      fontSize: Theme.heading.small.fontSize,
      lineHeight: Theme.heading.small.lineHeight,
    }),
    xSmall: css({
      fontSize: Theme.heading.xSmall.fontSize,
      lineHeight: Theme.heading.xSmall.lineHeight,
    }),
  };

  return style[size];
};
