import { css } from '@emotion/react';

import { Theme } from '@styles/Theme';

export const containerStyling = css({
  display: 'inline-block',
});

export const headerStyling = css({
  marginBottom: Theme.spacer.spacing3,

  textAlign: 'center',

  '& > h6': {
    fontWeight: 600,
  },
});

export const dayContainerStyling = css({
  display: 'grid',
  gridTemplateColumns: 'repeat(7, 40px)',
  gridAutoRows: '40px',
  columnGap: '2px',
  rowGap: '2px',
});

export const dayOfWeekContainerStyling = css({
  marginBottom: Theme.spacer.spacing2,

  '& div': {
    color: Theme.color.gray600,

    cursor: 'default',

    '& span:hover': {
      backgroundColor: Theme.color.white,
    },
  },
});
