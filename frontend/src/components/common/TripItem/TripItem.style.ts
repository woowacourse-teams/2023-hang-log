import { css } from '@emotion/react';

import { Theme } from 'hang-log-design-system';

export const getContainerStyling = ({
  isEditable,
  isDragging,
}: {
  isEditable?: boolean;
  isDragging?: boolean;
}) => {
  return css({
    position: 'relative',

    minHeight: '120px',
    borderRadius: Theme.borderRadius.medium,

    backgroundColor: 'white',

    opacity: isDragging ? '0.4' : '1',

    cursor: isEditable ? 'grab' : 'unset',
  });
};

export const contentContainerStyling = css({
  display: 'flex',
  gap: Theme.spacer.spacing4,

  '@media screen and (max-width: 600px)': {
    flexDirection: 'column',
    gap: Theme.spacer.spacing3,
  },
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

export const titleStyling = css({
  fontWeight: '600',
  wordBreak: 'break-all',
});

export const memoStyling = css({
  width: '95%',
  marginTop: Theme.spacer.spacing3,

  wordBreak: 'break-all',
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
