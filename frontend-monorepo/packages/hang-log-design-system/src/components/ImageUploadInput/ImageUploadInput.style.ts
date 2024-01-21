import { css } from '@emotion/react';

import { Theme } from '@styles/Theme';

export const inputContainerStyling = css({
  display: 'flex',
  flexDirection: 'column',
  gap: Theme.spacer.spacing2,
});

export const inputWrapperStyling = css({
  overflowX: 'auto',
  overflowY: 'hidden',
});

export const getUploadButtonStyling = (isUploaded: boolean, maxUploaded: boolean) => {
  return css({
    display: maxUploaded ? 'none' : 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    gap: Theme.spacer.spacing2,

    width: isUploaded ? '100px' : '100%',
    minWidth: isUploaded ? '100px' : '100%',
    height: '100px',

    fontWeight: 'normal',

    transition: 'none',
  });
};

export const inputStyling = css({
  display: 'none',
});

export const imageWrapperStyling = css({
  position: 'relative',

  minWidth: '100px',
  width: '100px',
  minHeight: '100px',
  height: '100px',
});

export const imageStyling = css({
  width: '100px',
  height: '100px',
  borderRadius: Theme.borderRadius.small,

  objectFit: 'cover',
});

export const deleteButtonStyling = css({
  position: 'absolute',
  right: 0,

  width: '36px',
  height: '36px',
  border: 'none',
  borderRadius: 0,
  borderTopRightRadius: Theme.borderRadius.small,
  outline: 0,

  backgroundColor: Theme.color.black,

  transition: 'all .2s ease-in',

  cursor: 'pointer',

  '&:hover': {
    backgroundColor: Theme.color.gray800,
  },

  '& svg': {
    width: '12px',
    height: '12px',

    '& > path': {
      stroke: Theme.color.white,
    },
  },
});
