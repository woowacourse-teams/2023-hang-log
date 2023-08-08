import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export const containerStyling = css({
  display: 'flex',
  flexDirection: 'column',
  alignItems: 'center',

  width: '50vw',
  padding: '0 50px',

  '@media screen and (max-width: 600px)': {
    width: '100vw',
    padding: `0 ${Theme.spacer.spacing4}`,
  },
});

export const totalAmountStyling = css({
  alignSelf: 'flex-start',

  marginBottom: Theme.spacer.spacing5,

  '& span': {
    fontWeight: 600,
  },
});
