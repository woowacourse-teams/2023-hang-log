import { css } from '@emotion/react';

import { Theme } from '@styles/Theme';

export const getToggleStyling = (isSelected: boolean) => {
  return css({
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',

    padding: '8px 12px',
    border: `1px solid ${isSelected ? Theme.color.blue700 : Theme.color.gray200}`,

    backgroundColor: isSelected ? Theme.color.blue100 : Theme.color.white,

    fontSize: Theme.text.small.fontSize,
    lineHeight: Theme.text.small.lineHeight,
    color: isSelected ? Theme.color.blue700 : Theme.color.gray600,

    transition: `all .2s ease-in`,

    cursor: 'pointer',

    '&:hover': {
      color: isSelected ? Theme.color.blue700 : Theme.color.gray700,
      backgroundColor: isSelected ? Theme.color.blue200 : Theme.color.gray100,
    },
  });
};
