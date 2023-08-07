import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export const headerStyling = css({
  position: 'sticky',
  top: 0,

  borderBottom: `1px solid ${Theme.color.gray200}`,
  zIndex: Theme.zIndex.overlayMiddle,

  backgroundColor: Theme.color.white,
  padding: `${Theme.spacer.spacing4} 50px`,

  '& > *': {
    cursor: 'pointer',
  },

  '@media (max-width: 600px)': {
    padding: `${Theme.spacer.spacing3} ${Theme.spacer.spacing4}`,
  },
});

export const imageStyling = css({
  minWidth: '32px',
  minHeight: '32px',
  border: 'none',
  outline: 0,
  borderRadius: '50%',

  backgroundColor: Theme.color.gray200,
});

export const menuListStyling = css({
  transform: 'translateY(36px)',

  '& > li': {
    padding: `${Theme.spacer.spacing2} ${Theme.spacer.spacing3}`,
  },
});
