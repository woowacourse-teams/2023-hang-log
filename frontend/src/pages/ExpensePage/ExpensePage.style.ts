import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export const containerStyling = css({
  minHeight: `calc(100vh - 81px - ${Theme.spacer.spacing6})`,

  marginBottom: Theme.spacer.spacing6,

  '@media screen and (max-width: 600px)': {
    flexDirection: 'column',
    gap: Theme.spacer.spacing5,
  },
});

export const dividerStyling = css({
  width: `calc(100vw - ${Theme.spacer.spacing6})`,
  margin: `0 ${Theme.spacer.spacing4}`,
});
