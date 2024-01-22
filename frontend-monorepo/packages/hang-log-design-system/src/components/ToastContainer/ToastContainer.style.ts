import { css } from '@emotion/react';

export const containerStyling = css({
  position: 'fixed',
  bottom: '24px',
  display: 'flex',
  flexDirection: 'column',
  gap: '16px',
  justifyContent: 'center',
  alignItems: 'center',

  width: '100%',

  zIndex: 9999,
});
