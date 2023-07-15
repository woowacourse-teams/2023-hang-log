import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export const containerStyling = css({
  display: 'flex',
  flexDirection: 'column',
  gap: Theme.spacer.spacing4,

  width: '50vw',
  padding: `${Theme.spacer.spacing4} 50px`,
});
