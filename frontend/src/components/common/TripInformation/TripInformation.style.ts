import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export const sectionStyling = css({
  position: 'relative',
  width: '100%',
  minHeight: '240px',
  padding: `${Theme.spacer.spacing4} 50px`,

  '@media screen and (max-width: 600px)': {
    minHeight: '220px',
    padding: Theme.spacer.spacing4,
  },

  '& *': {
    color: Theme.color.white,
  },
});

export const imageWrapperStyling = css({
  position: 'absolute',
  top: '0',
  left: '0',
  zIndex: -1,

  width: '100%',
  height: '100%',

  '& > div': {
    position: 'absolute',
    width: '100%',
    height: '100%',
    zIndex: Theme.zIndex.overlayTop,

    backgroundColor: `rgba(0, 0, 0, .4)`,
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
  right: Theme.spacer.spacing4,
  display: 'flex',
  alignItems: 'center',
  gap: Theme.spacer.spacing1,
});

export const badgeStyling = css({
  color: `${Theme.color.blue700} !important`,
});

export const badgeWrapperStyling = css({
  width: '60%',
  minHeight: '24px',
  marginBottom: Theme.spacer.spacing2,

  overflowX: 'scroll',
  whiteSpace: 'nowrap',
  '-ms-overflow-style': 'none',
  scrollbarWidth: 'none',

  '& > span': {
    marginRight: Theme.spacer.spacing1,
  },

  '::-webkit-scrollbar': {
    display: 'none',
  },

  '@media screen and (max-width: 600px)': {
    width: 'calc(100vw - 220px)',
  },
});
