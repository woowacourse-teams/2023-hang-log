import { css } from '@emotion/react';

import { Theme } from 'hang-log-design-system';

export const shareButtonStyling = css({
  marginLeft: Theme.spacer.spacing3,
  border: 'none',

  backgroundColor: 'transparent',

  cursor: 'pointer',

  '& svg': {
    width: '20px',
    height: '20px',

    '& path': {
      stroke: Theme.color.white,
      strokeWidth: 1.5,
    },
  },
});

export const shareContainerStyling = css({
  display: 'flex',
  flexDirection: 'column',
  width: '300px',
  gap: '18px',
  transform: ' translateY(35px)',

  padding: Theme.spacer.spacing3,

  fontSize: Theme.text.small.fontSize,
  lineHeight: Theme.text.small.lineHeight,

  '@media screen and (max-width:600px)': {
    width: '230px',
  },
});

export const shareItemStyling = css({
  display: 'flex',
  justifyContent: 'space-between',
  alignItems: 'center',
});

export const shareUrlWrapperStyling = css({
  display: 'flex',
  justifyContent: 'space-between',
  alignItems: 'center',
  width: '100%',

  border: `1px solid ${Theme.color.gray200}`,
  borderRadius: Theme.borderRadius.small,

  backgroundColor: Theme.color.gray100,
});

export const shareUrlStyling = css({
  padding: `${Theme.spacer.spacing1} ${Theme.spacer.spacing2}`,

  overflow: 'hidden',

  color: Theme.color.black,
});

export const copyButtonStyling = css({
  minWidth: 'fit-content',
  padding: `${Theme.spacer.spacing2} ${Theme.spacer.spacing3}`,
  border: 'none',
  borderLeft: `1px solid ${Theme.color.gray200}`,

  backgroundColor: Theme.color.white,
  color: Theme.color.black,

  cursor: 'pointer',
});
