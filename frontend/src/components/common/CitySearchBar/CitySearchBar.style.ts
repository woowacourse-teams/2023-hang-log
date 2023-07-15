import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export const container = css({
  width: '500px',
  margin: '0 auto',
});

export const wrapper = css({
  display: 'flex',
  alignItems: 'center',
  gap: Theme.spacer.spacing1,

  minHeight: '60px',
  padding: Theme.spacer.spacing2,
  border: `1px solid ${Theme.color.gray100}`,
  borderRadius: Theme.borderRadius.small,

  backgroundColor: Theme.color.gray100,
});

export const searchPinIconStyling = css({
  position: 'fixed',
});

export const inputStyling = css({
  width: 'fit-content',
});

export const tagListStyling = css({
  display: 'flex',
  flexWrap: 'wrap',
  gap: Theme.spacer.spacing1,

  width: '90%',
  marginLeft: Theme.spacer.spacing2,
});

export const badgeStyling = css({
  display: 'flex',
  alignItems: 'center',
  gap: Theme.spacer.spacing2,
});

export const closeIconStyling = css({
  width: '8px',
  height: '8px',

  cursor: 'pointer',

  path: {
    stroke: Theme.color.blue700,
    strokeWidth: '2px',
  },
});
