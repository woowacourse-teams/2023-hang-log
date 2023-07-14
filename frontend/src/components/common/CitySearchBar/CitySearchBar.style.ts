import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export const container = css({
  margin: '0 auto',
  width: '500px',
});

export const wrapper = css({
  display: 'flex',
  alignItems: 'center',

  height: '60px',

  padding: Theme.spacer.spacing2,
  gap: Theme.spacer.spacing1,

  border: `1px solid ${Theme.color.gray100}`,
  borderRadius: Theme.borderRadius.small,
  backgroundColor: Theme.color.gray100,

  overflow: 'hidden',
});

export const searchPinIconStyling = css({
  position: 'fixed',
});

export const inputStyling = css({});

export const suggestionContainer = css({
  //왜 100%가 안먹는거지? 부모가 500px인뎅?
  width: '500px',
  transform: 'translateY(0)',
});

export const emptyTextStyling = css({
  color: Theme.color.gray500,
  margin: Theme.spacer.spacing2,
});

export const tagListStyling = css({
  display: 'flex',
  gap: Theme.spacer.spacing1,
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

  path: {
    stroke: Theme.color.blue700,
    strokeWidth: '2px',
  },

  cursor: 'pointer',
});
