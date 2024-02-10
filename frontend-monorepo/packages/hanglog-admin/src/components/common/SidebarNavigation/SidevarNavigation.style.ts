import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export const containerStyling = css({
  backgroundColor: Theme.color.gray100,
  width: '20vw',
  height: 'calc(100vh - 81px)',
  paddingTop: Theme.spacer.spacing6,

  '@media screen and (max-width: 600px)': {
    height: 'calc(100vh - 65px)',
  },
});

export const tabStyling = css({
  minWidth: '15vw',
});
