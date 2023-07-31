import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export const containerStyling = css({
  display: 'flex',
  flexDirection: 'column',
  gap: Theme.spacer.spacing3,

  width: '50vw',
});

export const toggleGroupStyling = css({
  alignSelf: 'flex-end',
});
