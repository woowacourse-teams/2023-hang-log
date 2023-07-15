import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export const getContainerStyling = (isDragging?: boolean) =>
  css({
    position: 'relative',

    minHeight: '120px',
    borderRadius: Theme.borderRadius.medium,

    backgroundColor: 'white',

    opacity: isDragging ? '0.4' : '1',

    cursor: 'grab',
  });

export const informationContainerStyling = css({
  width: '100%',
});

export const subInformationStyling = css({
  marginTop: '2px',

  color: Theme.color.gray600,
});

export const starRatingStyling = css({
  marginTop: Theme.spacer.spacing2,
});

export const memoStyling = css({
  marginTop: Theme.spacer.spacing3,
});

export const expenseStyling = css({
  marginTop: Theme.spacer.spacing3,
});

export const moreButtonStyling = css({
  position: 'absolute',

  width: '20px',
  height: '20px',
  border: 'none',
  outline: 0,

  backgroundColor: 'transparent',

  cursor: 'pointer',

  '& svg': {
    width: '20px',
    height: '20px',
  },
});

export const moreMenuStyling = css({
  position: 'absolute',
  top: 0,
  right: 0,
});

export const moreMenuListStyling = css({
  minWidth: 'unset',
  width: 'max-content',

  transform: 'translateY(16px)',

  '& > li': {
    padding: `${Theme.spacer.spacing2} ${Theme.spacer.spacing3}`,
  },
});
