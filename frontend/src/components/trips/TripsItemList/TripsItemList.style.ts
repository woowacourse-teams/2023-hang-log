import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export const containerStyling = css({
  minHeight: 'calc(100vh - 331px)',

  '@media screen and (max-width: 600px)': {
    minHeight: 'calc(100vh - 48px)',
  },
});

export const gridBoxStyling = css({
  margin: `${Theme.spacer.spacing4} 50px ${Theme.spacer.spacing7}`,
  display: 'grid',
  gridTemplateColumns: 'repeat(5, 1fr)',
  rowGap: Theme.spacer.spacing5,
  columnGap: Theme.spacer.spacing4,
  placeItems: 'center',

  '@media screen and (max-width: 600px)': {
    display: 'grid',
    gridTemplateColumns: 'repeat(1, 1fr)',
    placeItems: 'center',
    margin: `${Theme.spacer.spacing4} 24px ${Theme.spacer.spacing7}`,
  },
});

export const toggleGroupStyling = css({
  marginTop: Theme.spacer.spacing3,
  '@media screen and (max-width: 600px)': {
    display: 'none',
  },
});

export const emptyBoxStyling = css({
  marginLeft: '50px',
});
