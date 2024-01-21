import { css } from '@emotion/react';

import type { SelectProps } from '@components/Select/Select';

import { Theme } from '@styles/Theme';

export const selectContainerStyling = css({
  display: 'flex',
  flexDirection: 'column',
  gap: Theme.spacer.spacing2,
});

export const getSelectWrapperStyling = (isError: Required<SelectProps>['isError']) => {
  return css({
    position: 'relative',
    paddingRight: Theme.spacer.spacing3,
    backgroundColor: isError ? Theme.color.red100 : Theme.color.gray100,
    border: `1px solid ${isError ? Theme.color.red200 : 'transparent'}`,
    borderRadius: Theme.borderRadius.small,
  });
};

export const getSizeStyling = (size: Required<SelectProps>['size']) => {
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

export const getSelectStyling = (isError: Required<SelectProps>['isError']) => {
  return css({
    width: '100%',
    backgroundColor: isError ? Theme.color.red100 : Theme.color.gray100,
    border: 'none',
    borderRadius: Theme.borderRadius.small,
    outline: 0,
  });
};
