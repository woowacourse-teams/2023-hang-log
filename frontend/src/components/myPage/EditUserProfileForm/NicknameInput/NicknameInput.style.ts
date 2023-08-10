import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export const inputStyling = css({
  width: `calc(360px - ${Theme.spacer.spacing5})`,

  '@media screen and (max-width: 600px)': {
    width: `calc(100vw - 80px)`,
  },
});
