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

export const switchToggleStyling = css({
  appearance: 'none',
  position: 'relative',
  width: '30px',
  height: '16px',

  boxSizing: 'content-box',
  backgroundColor: Theme.color.gray300,
  border: `4px solid ${Theme.color.gray300}`,
  borderColor: Theme.color.gray300,
  borderRadius: '16px',

  cursor: 'pointer',

  '&::before': {
    position: 'absolute',
    left: 0,
    width: '16px',
    height: '16px',

    backgroundColor: Theme.color.white,
    borderRadius: '50%',

    content: '""',
    transition: 'left 250ms linear',
  },

  '&:checked': {
    backgroundColor: Theme.color.blue600,
    border: `4px solid ${Theme.color.blue600}`,
  },

  '&:checked::before': {
    left: '14px',
  },
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
  padding: '4px 8px',

  overflow: 'hidden',

  color: Theme.color.black,
});

export const copyButtonStyling = css({
  width: '75px',
  padding: '8px 4px',

  border: 'none',
  borderLeft: `1px solid ${Theme.color.gray200}`,
  backgroundColor: Theme.color.white,

  color: Theme.color.black,

  cursor: 'pointer',
});
