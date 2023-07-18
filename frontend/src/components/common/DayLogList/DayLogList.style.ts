import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export const containerStyling = css({
  display: 'flex',
  flexDirection: 'column',
  gap: Theme.spacer.spacing4,

  width: '100%',
  padding: `${Theme.spacer.spacing4} 50px`,

  '& > ul': {
    width: '100%',
  },
});
