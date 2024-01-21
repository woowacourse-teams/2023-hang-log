import { css } from '@emotion/react';

import { Theme } from '@styles/Theme';

export const dayContainerStyling = css({
  minWidth: '40px',
  width: '40px',
  height: '40px',
});

export const getDayStyling = (isClickable: boolean) => {
  return css({
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',

    width: '40px',
    height: '40px',
    backgroundColor: Theme.color.white,

    fontSize: Theme.text.small.fontSize,
    fontWeight: '500',

    cursor: isClickable ? 'pointer' : 'default',

    '&:hover': {
      backgroundColor: Theme.color.gray100,
    },
  });
};

export const getTodayStyling = (isToday: boolean) => {
  return (
    isToday &&
    css({
      backgroundColor: Theme.color.blue300,

      color: Theme.color.blue800,

      '&:hover': {
        backgroundColor: Theme.color.blue300,
      },
    })
  );
};

export const getDayInRangeStyling = (isInRange: boolean) => {
  return (
    isInRange &&
    css({
      backgroundColor: Theme.color.blue100,

      color: Theme.color.blue600,

      '&:hover': {
        backgroundColor: Theme.color.blue100,
      },
    })
  );
};

export const getSelectedDayStyling = (isSelected: boolean) => {
  return (
    isSelected &&
    css({
      backgroundColor: Theme.color.blue500,

      color: Theme.color.white,

      '&:hover': {
        backgroundColor: Theme.color.blue500,
      },
    })
  );
};

export const getDisabledDayStyling = (isDisabled: boolean) => {
  return (
    isDisabled &&
    css({
      backgroundColor: Theme.color.white,

      color: Theme.color.gray500,

      pointerEvents: 'none',

      '&:hover': {
        backgroundColor: Theme.color.white,
      },
    })
  );
};
