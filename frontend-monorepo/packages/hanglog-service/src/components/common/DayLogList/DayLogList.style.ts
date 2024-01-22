import { css } from '@emotion/react';

import { Theme } from 'hang-log-design-system';

export const containerStyling = css({
  display: 'flex',
  flexDirection: 'column',
  gap: Theme.spacer.spacing4,

  width: '100%',
  padding: `${Theme.spacer.spacing4} 50px`,

  '@media screen and (max-width: 600px)': {
    padding: `${Theme.spacer.spacing2} ${Theme.spacer.spacing4}`,
  },

  '& > ul': {
    width: '100%',
  },
});
