import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export const containerStyling = css({
  alignItems: 'center',
  alignSelf: 'flex-start',

  width: '50%',
  margin: '0 auto',

  cursor: 'pointer',
});

export const inputStyling = css({
  width: '400px',
  marginTop: Theme.spacer.spacing2,

  cursor: 'pointer',
});

export const calendarStyling = css({
  marginTop: Theme.spacer.spacing2,

  border: `1px solid ${Theme.color.gray200}`,
  borderRadius: Theme.borderRadius.small,
});
