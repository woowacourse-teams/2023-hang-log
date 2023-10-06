import { css } from '@emotion/react';

import { Theme } from 'hang-log-design-system';

export const containerStyling = css({
  '@media screen and (max-width: 600px)': {
    minHeight: `calc(100vh - ${Theme.spacer.spacing6})`,
  },
});

export const gridBoxStyling = css({
  display: 'grid',
  gridTemplateColumns: 'repeat(5, 1fr)',
  rowGap: Theme.spacer.spacing5,
  columnGap: Theme.spacer.spacing4,
  placeItems: 'center',

  margin: `${Theme.spacer.spacing4} 50px 0px`,

  '@media screen and (max-width: 600px)': {
    display: 'grid',
    gridTemplateColumns: 'repeat(1, 1fr)',
    placeItems: 'center',

    margin: `${Theme.spacer.spacing4} 24px ${Theme.spacer.spacing7}`,
  },
});

export const headingStyling = css({
  fontSize: Theme.heading.small.fontSize,

  '@media screen and (max-width: 600px)': {
    fontSize: Theme.heading.xSmall.fontSize,
    lineHeight: Theme.heading.xSmall.lineHeight,
  },
});

export const toggleGroupStyling = css({
  marginTop: Theme.spacer.spacing3,

  '@media screen and (max-width: 600px)': {
    display: 'none',
  },
});

export const textStyling = css({
  padding: '8px 0 16px',
  fontSize: Theme.text.large.fontSize,

  '@media screen and (max-width: 600px)': {
    fontSize: Theme.text.medium.fontSize,
    lineHeight: Theme.text.medium.lineHeight,
  },
});

export const emptyBoxStyling = css({
  marginLeft: '50px',

  '@media screen and (max-width: 600px)': {
    marginLeft: Theme.spacer.spacing4,
  },
});
