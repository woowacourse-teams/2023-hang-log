import { css } from '@emotion/react';

import { Theme } from '@styles/Theme';

export const containerStyling = (width: number, height: number) => {
  return css({
    position: 'relative',

    width,
    height,
    minWidth: width,
    minHeight: height,

    borderRadius: Theme.borderRadius.medium,

    overflow: 'hidden',

    '& *': {
      userSelect: 'none',
    },
  });
};

export const sliderWrapperStyling = (width: number, height: number) =>
  css({
    display: 'flex',
    width: '100%',
    margin: 0,
    padding: 0,
    height,

    overflow: 'hidden',
  });

export const carouselItemStyling = (width: number, height: number) =>
  css({
    display: 'flex',

    '& > *': {
      objectFit: 'cover',
      width,
      height,
    },
  });

export const itemWrapperStyling = (width: number, height: number) => {
  return css({
    minWidth: width,
    width,
    minHeight: height,
    height,

    '& > img': {
      width,
      height,

      backgroundColor: Theme.color.gray200,

      objectFit: 'cover',
    },
  });
};

export const buttonContainerStyling = (showOnHover: boolean) =>
  css({
    transition: 'opacity .1s ease-in',

    opacity: showOnHover ? 0 : 1,

    'div:hover &': {
      opacity: 1,
    },

    '& > button': {
      position: 'absolute',
      top: '50%',
      display: 'flex',
      justifyContent: 'center',
      alignItems: 'center',
      zIndex: Theme.zIndex.overlayTop,

      width: '28px',
      height: '28px',
      border: 'none',
      borderRadius: '50%',
      outline: '0',

      backgroundColor: Theme.color.white,
      boxShadow: Theme.boxShadow.shadow8,

      transform: 'translateY(-50%)',

      cursor: 'pointer',

      '& svg': {
        width: '12px',
        height: '12px',

        '& path': {
          strokeWidth: 2,
        },
      },
    },
  });

export const leftButtonStyling = css({
  left: Theme.spacer.spacing2,
});

export const rightButtonStyling = css({
  right: Theme.spacer.spacing2,
});

export const dotStyling = (isSelected: boolean) => {
  return css({
    width: '6px',
    height: '6px',
    borderRadius: '50%',

    backgroundColor: Theme.color.white,

    opacity: isSelected ? 1 : 0.6,
  });
};
