import { css } from '@emotion/react';

import { Theme } from 'hang-log-design-system';

export const containerStyling = css({
  margin: `0 ${Theme.spacer.spacing5}`,
  marginTop: Theme.spacer.spacing6,
  marginBottom: Theme.spacer.spacing8,

  '@media screen and (max-width: 600px)': {
    minHeight: 'calc(100vh - 65px)',
    margin: `0 ${Theme.spacer.spacing4}`,
    marginTop: Theme.spacer.spacing6,
    marginBottom: Theme.spacer.spacing6,
  },
});

export const titleStyling = css({
  color: Theme.color.gray800,
  fontSize: '56px',
  fontWeight: 'normal',
  textAlign: 'center',
  wordBreak: 'keep-all',

  '@media screen and (max-width: 600px)': {
    fontSize: Theme.heading.large.fontSize,
    lineHeight: Theme.heading.large.lineHeight,
  },

  '& > span': {
    color: Theme.color.blue500,
    fontWeight: 'bold',
  },
});

export const headingStyling = css({
  fontWeight: 'normal',
  textAlign: 'center',

  '@media screen and (max-width: 600px)': {
    fontSize: Theme.text.large.fontSize,
    lineHeight: Theme.text.large.lineHeight,
  },
});

export const imageStyling = css({
  width: '1050px',
  maxWidth: 'calc(100vw - 100px)',
  marginTop: Theme.spacer.spacing3,
  borderRadius: Theme.borderRadius.large,

  boxShadow: Theme.boxShadow.shadow9,

  '@media screen and (max-width: 600px)': {
    maxWidth: 'calc(100vw - 48px)',
  },
});

export const subTitleStyling = (topPadding: number = 0, bottomPadding: number = 0) => {
  return css({
    paddingTop: `${topPadding}px`,
    paddingLeft: '50px',
    paddingBottom: `${bottomPadding}px`,

    fontSize: Theme.heading.small.fontSize,
    fontWeight: 600,
  });
};
