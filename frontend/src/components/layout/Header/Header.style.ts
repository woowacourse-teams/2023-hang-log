import { css } from '@emotion/react';

import { Theme } from 'hang-log-design-system';

export const headerStyling = css({
  position: 'sticky',
  top: 0,

  height: '81px',
  borderBottom: `1px solid ${Theme.color.gray200}`,
  zIndex: Theme.zIndex.overlayMiddle,

  backgroundColor: Theme.color.white,
  padding: `${Theme.spacer.spacing4} 50px`,

  '@media screen and (max-width: 600px)': {
    height: '65px',
    padding: `${Theme.spacer.spacing3} ${Theme.spacer.spacing4}`,
  },
});

export const getItemStyling = (isLoggedIn: boolean) => {
  return css({
    position: 'relative',
    top: !isLoggedIn ? '-2px' : 'undefined',

    cursor: 'pointer',
  });
};

export const getTapNavigateButtonStyling = (isLoggedIn: boolean, isSelected: boolean) => {
  return css({
    display: isLoggedIn ? 'block' : 'none',

    color: isSelected ? Theme.color.blue700 : Theme.color.gray600,
    fontWeight: 700,
    fontSize: Theme.text.small.fontSize,

    cursor: 'pointer',
  });
};
