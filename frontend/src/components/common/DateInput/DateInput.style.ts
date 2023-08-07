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

    '> *': {
      '> *:nth-of-type(odd)': {
        backgroundColor: isCalendarOpen ? Theme.color.white : Theme.color.gray100,
        boxShadow: isCalendarOpen ? Theme.boxShadow.shadow8 : '',
        cursor: 'pointer',
      },
      '> *': {
        '> input': { cursor: 'pointer' },
      },
    },

    '@media (max-width: 600px)': {
      width: '100%',
    },
  });
};

export const calendarStyling = css({
  position: 'absolute',
  transform: 'translateY(60px)',
  marginTop: Theme.spacer.spacing2,

  border: `1px solid ${Theme.color.gray200}`,
  borderRadius: Theme.borderRadius.small,
  backgroundColor: Theme.color.white,
  boxShadow: Theme.boxShadow.shadow8,

  zIndex: Theme.zIndex.overlayPeak,

  '@media (max-width: 600px)': {
    height: '350px',
    overflow: 'auto',
  },
});
