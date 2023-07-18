import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export const headerStyling = css({
  position: 'sticky',
  top: 0,

  border: `1px solid ${Theme.color.gray200}`,
  zIndex: Theme.zIndex.overlayMiddle,

  backgroundColor: Theme.color.white,
  padding: `${Theme.spacer.spacing4} 50px`,

  '& > *': {
    cursor: 'pointer',
  },
});

export const imageStyling = css({
  minWidth: '32px',
  minHeight: '32px',
  borderRadius: '50%',

  backgroundColor: Theme.color.gray200,
});
