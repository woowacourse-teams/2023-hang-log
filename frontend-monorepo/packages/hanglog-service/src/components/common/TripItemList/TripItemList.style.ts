import { css } from '@emotion/react';

import { Theme } from 'hang-log-design-system';

export const containerStyling = css({
  display: 'flex',
  flexDirection: 'column',
  gap: Theme.spacer.spacing4,
});

export const emptyTextStyling = css({
  marginTop: Theme.spacer.spacing1,
});

export const addItemButtonStyling = css({
  marginTop: Theme.spacer.spacing4,
});
