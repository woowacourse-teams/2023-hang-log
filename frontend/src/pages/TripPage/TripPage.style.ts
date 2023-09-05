import { css } from '@emotion/react';

import { Theme } from 'hang-log-design-system';

export const containerStyling = css({
  position: 'relative',

  width: '50vw',

  paddingBottom: '114px',

  '@media screen and (max-width: 1200px)': {
    width: '60vw',
  },

  '@media screen and (max-width: 600px)': {
    width: '100vw',
    paddingBottom: '0',
  },
});

export const mapContainerStyling = css({
  position: 'sticky',
  top: '81px',
  left: '50vw',

  width: '50vw',
  height: 'calc(100vh - 81px)',
});

export const mapMobileContainerStyling = css({
  position: 'sticky',

  width: '100vw',
  height: 'calc(100vh - 65px)',
});

export const skeletonContainerStyling = css({
  '& > div:first-of-type': {
    borderRadius: 0,
  },
});

export const buttonContainerStyling = css({
  position: 'fixed',
  bottom: '30px',
  display: 'flex',
  width: '100%',
  alignItems: 'center',
  justifyContent: 'center',
});

export const contentStyling = css({
  display: 'flex',
  flexDirection: 'column',
  gap: Theme.spacer.spacing4,

  width: '100%',
  padding: `${Theme.spacer.spacing4} 50px`,

  '@media screen and (max-width: 600px)': {
    padding: Theme.spacer.spacing4,
  },

  '& > ul': {
    width: '100%',
  },
});

export const buttonStyling = css({
  borderRadius: '40px',
  boxShadow: Theme.boxShadow.shadow8,
});
