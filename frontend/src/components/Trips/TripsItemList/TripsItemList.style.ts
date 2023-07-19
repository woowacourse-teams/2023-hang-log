import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export const gridBoxStyling = css({
  margin: `${Theme.spacer.spacing4} 50px ${Theme.spacer.spacing7}`,
  display: 'grid',
  gridTemplateColumns: 'repeat(5, 1fr)',
  rowGap: Theme.spacer.spacing5,
  columnGap: Theme.spacer.spacing4,
  placeItems: 'center',
});

export const toggleGroupStyling = css({
  marginTop: Theme.spacer.spacing3,
});

export const emptyBoxStyling = css({
  marginLeft: '50px',
});
