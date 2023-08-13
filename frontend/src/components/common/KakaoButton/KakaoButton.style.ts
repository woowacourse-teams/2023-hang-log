import { css } from '@emotion/react';

import { Theme } from 'hang-log-design-system';

export const buttonStyling = css({
  display: 'flex',
  justifyContent: 'center',

  backgroundColor: '#FFDC00',

  padding: `${Theme.spacer.spacing3} 12px`,
  border: 'none',
  borderRadius: Theme.borderRadius.small,
  outline: 0,

  color: Theme.color.gray800,
  fontSize: Theme.text.medium.fontSize,
  lineHeight: Theme.text.medium.lineHeight,
  fontWeight: 'bold',
});
