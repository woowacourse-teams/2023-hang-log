import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export const containerStyling = css({
  width: '80vw',
  padding: `${Theme.spacer.spacing6} ${Theme.spacer.spacing6} ${Theme.spacer.spacing0} ${Theme.spacer.spacing6}`,
});

export const titleStyling = css({
  alignSelf: 'flex-start',
});

export const addButtonStyling = css({
  margin: Theme.spacer.spacing3,
  alignSelf: 'flex-end',
});

export const tableStyling = css({
  width: '70vw',
  height: '60vh',
});

export const pagenationSkeletonStyling = css({
  display: 'flex',
  justifyContent: 'center',
  alignItems: 'center',

  padding: `${Theme.spacer.spacing6} 0px ${Theme.spacer.spacing6} 0px`,

  width: '100%',
  height: Theme.spacer.spacing6,

  '& > div': {
    width: '300px',
  },
});
