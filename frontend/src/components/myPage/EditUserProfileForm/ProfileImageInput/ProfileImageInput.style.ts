import { css } from '@emotion/react';

import { Theme } from 'hang-log-design-system';

export const wrapperStyling = css({
  position: 'relative',
  width: '200px',
});

export const uploadButtonStyling = css({
  position: 'absolute',
  left: '50%',
  bottom: '-16px',
  display: 'flex',
  alignItems: 'center',
  gap: Theme.spacer.spacing2,

  paddingLeft: Theme.spacer.spacing4,
  paddingRight: Theme.spacer.spacing4,
  borderRadius: Theme.spacer.spacing7,

  boxShadow: Theme.boxShadow.shadow8,

  transform: 'translateX(-50%)',
});

export const inputStyling = css({
  display: 'none',
});

export const imageStyling = css({
  width: '200px',
  height: '200px',
  borderRadius: '50%',

  objectFit: 'cover',
});
