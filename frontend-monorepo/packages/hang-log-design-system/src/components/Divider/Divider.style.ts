import { css } from '@emotion/react';

import type { DividerProps } from '@components/Divider/Divider';

import { Theme } from '@styles/Theme';

export const getDirectionStyling = (
  direction: Required<DividerProps>['direction'],
  length: Required<DividerProps>['length']
) => {
  const style = {
    horizontal: css({
      borderBottom: `1px solid ${Theme.color.gray200}`,
      width: length,
    }),
    vertical: css({
      borderLeft: `1px solid ${Theme.color.gray200}`,
      height: length,
    }),
  };

  return style[direction];
};
