import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export const TripsItemGridBoxStyling = css({
  margin: `${Theme.spacer.spacing4} 50px ${Theme.spacer.spacing7}`,
  display: 'grid',
  gridTemplateColumns: 'repeat(5, 1fr)',
  rowGap: Theme.spacer.spacing5,
  columnGap: '24px',
  placeItems: 'center',
});

export const TripsToggleGroupStyling = css({
  marginTop: Theme.spacer.spacing3,
});
