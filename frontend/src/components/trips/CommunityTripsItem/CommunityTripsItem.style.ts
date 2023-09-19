import { css } from '@emotion/react';

import { Theme } from 'hang-log-design-system';

export const boxStyling = css({
  alignSelf: 'start',

  width: `calc((100vw - 196px) / 5)`,
  margin: 0,
  padding: 0,

  cursor: 'pointer',
  '@media screen and (max-width: 600px)': {
    width: `calc(100vw - 48px)`,
  },
});

export const imageBoxStyling = css({
  width: '100%',
  height: '176px',
});

export const imageStyling = css({
  objectFit: 'cover',

  width: '100%',
  height: '100%',
  minHeight: `calc(((100vw - 196px) / 24) * 3)`,
  maxHeight: `calc(((100vw - 196px) / 24) * 3)`,

  borderRadius: Theme.borderRadius.medium,

  backgroundColor: Theme.color.gray200,

  '@media screen and (max-width: 600px)': {
    minHeight: '256px',
    maxHeight: '256px',
  },
});

export const nameStyling = css({
  marginBottom: Theme.spacer.spacing1,

  fontWeight: '600',
});

export const badgeBoxStyling = css({
  width: `calc((100vw - 196px) / 5)`,
  minHeight: '22px',
  marginTop: Theme.spacer.spacing3,
  marginBottom: Theme.spacer.spacing2,

  overflowX: 'scroll',
  whiteSpace: 'nowrap',
  scrollbarWidth: 'none',
  '-ms-overflow-style': 'none',

  '& > span': {
    marginRight: Theme.spacer.spacing1,
  },

  '::-webkit-scrollbar': {
    display: 'none',
  },

  '@media screen and (max-width: 600px)': {
    width: '100%',
  },
});

export const durationAndDescriptionStyling = css({
  marginBottom: Theme.spacer.spacing1,

  '@media screen and (max-width: 600px)': {
    width: 'calc(100vw - 48px)',
  },
});

export const durationTextStyling = css({
  display: '-webkit-box',

  marginTop: '4px',
  width: '100%',

  color: Theme.color.gray700,

  textOverflow: 'ellipsis',
  overflow: 'hidden',
  wordBreak: 'break-word',
  '-webkit-line-clamp': '2',
  '-webkit-box-orient': 'vertical',
});

export const skeletonDurationTextStyling = css({
  marginTop: '2px',
});

export const clickableLikeStyling = css({
  position: 'absolute',

  transform: 'translateX(calc((100vw - 356px) / 5)) translateY(12px)',

  '@media screen and (max-width: 600px)': {
    transform: 'translateX(calc(100vw - 80px)) translateY(12px)',
  },
});

export const communityItemInfoStyling = css({
  paddingTop: '12px',

  color: Theme.color.gray700,

  fontSize: Theme.text.small.fontSize,
  lineHeight: '20px',
  fontWeight: 400,
});

export const likeCountBoxStyling = css({
  display: 'flex',
  justifyContent: 'center',
  alignItems: 'center',
  gap: Theme.spacer.spacing2,
});

export const informationStyling = css({
  display: 'flex',
  flexDirection: 'column',
  justifyContent: 'space-between',
  height: '100%',
});
