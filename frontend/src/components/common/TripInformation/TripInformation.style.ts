import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export const sectionStyling = css({
  position: 'relative',
  width: '50vw',
  minHeight: '240px',
  padding: `${Theme.spacer.spacing4} 50px`,

  '& *': {
    color: Theme.color.white,
  },
});

export const imageWrapperStyling = css({
  position: 'absolute',
  top: '0',
  left: '0',

  width: '50vw',
  height: '100%',
  zIndex: -1,

  '& > div': {
    position: 'absolute',
    width: '50vw',
    height: '100%',

    backgroundColor: `rgba(0, 0, 0, .4)`,
    zIndex: Theme.zIndex.overlayTop,
  },

  '& > img': {
    width: '100%',
    height: '100%',

    objectFit: 'cover',
  },
});

export const titleStyling = css({
  marginTop: Theme.spacer.spacing3,
  marginBottom: Theme.spacer.spacing3,
});

export const descriptionStyling = css({
  marginTop: Theme.spacer.spacing3,
});

export const buttonContainerStyling = css({
  position: 'absolute',
  top: Theme.spacer.spacing4,
  right: '50px',
  display: 'flex',
  gap: Theme.spacer.spacing1,
});

export const editButtonStyling = css({
  backgroundColor: 'transparent',

  color: Theme.color.white,

  '&:hover:enabled': {
    backgroundColor: 'rgba(255, 255, 255, .2)',
  },
});
