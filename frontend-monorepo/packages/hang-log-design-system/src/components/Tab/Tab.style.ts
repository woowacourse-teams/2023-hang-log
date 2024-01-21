import { css } from '@emotion/react';

import { Theme } from '@styles/Theme';

export type TabSelectedStylingProps = 'outline' | 'block';

export const tabStyling = css({
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'center',

  minWidth: 'max-content',
  padding: '12px 16px',

  fontSize: Theme.text.medium.fontSize,
  lineHeight: Theme.text.medium.lineHeight,

  transition: 'all .2s ease-in',

  cursor: 'pointer',
});

export const getVariantStyling = (variant: TabSelectedStylingProps, isSelected: boolean) => {
  const style = {
    outline: css({
      borderBottom: `1px solid ${isSelected ? Theme.color.blue600 : 'transparent'}`,

      backgroundColor: Theme.color.white,

      color: isSelected ? Theme.color.blue600 : Theme.color.gray500,

      '&:hover': {
        color: isSelected ? Theme.color.blue600 : Theme.color.gray600,
      },
    }),

    block: css({
      border: 'none',

      backgroundColor: isSelected ? Theme.color.blue600 : Theme.color.gray100,

      color: isSelected ? Theme.color.white : Theme.color.gray500,

      '&:hover': {
        color: isSelected ? Theme.color.white : Theme.color.gray600,
      },
    }),
  };

  return style[variant];
};
