import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export const containerStyling = css({
  alignItems: 'center',
  alignSelf: 'flex-start',

  width: '100%',
  margin: '0 auto',

  cursor: 'pointer',
});

export const getInputStyling = (isCalendarOpen: boolean) => {
  return css({
    width: '400px',
    marginTop: Theme.spacer.spacing2,

    cursor: 'pointer',

    '> *': {
      '> *:nth-child(odd)': {
        backgroundColor: isCalendarOpen ? Theme.color.white : Theme.color.gray200,
        boxShadow: isCalendarOpen ? Theme.boxShadow.shadow8 : '',
        border: `1px solid ${Theme.color.gray200}`,
      },
    },
  });
};

export const calendarStyling = css({
  marginTop: Theme.spacer.spacing2,

  border: `1px solid ${Theme.color.gray200}`,
  borderRadius: Theme.borderRadius.small,
});
