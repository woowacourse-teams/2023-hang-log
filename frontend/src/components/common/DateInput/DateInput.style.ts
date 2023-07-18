import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export const containerStyling = css({
  alignItems: 'center',

  width: '50%',
  margin: '0 auto',

  cursor: 'pointer',
});

export const inputStyling = css({
  width: '500px',
  marginTop: Theme.spacer.spacing2,
});

export const calendarStyling = css({
  marginTop: Theme.spacer.spacing2,

  border: `1px solid ${Theme.color.gray200}`,
  borderRadius: Theme.borderRadius.small,
});
