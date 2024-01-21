import { css } from '@emotion/react';

import { Theme } from '@styles/Theme';

export const labelStyling = css({
  fontSize: Theme.text.small.fontSize,
  lineHeight: Theme.text.small.lineHeight,
  fontWeight: 600,
});

export const requiredStyling = css({
  color: Theme.color.red300,
});
