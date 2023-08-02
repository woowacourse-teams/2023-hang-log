import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export const containerStyling = css({
  minHeight: `calc(100vh - 81px - ${Theme.spacer.spacing6})`,

  marginBottom: Theme.spacer.spacing6,
});
