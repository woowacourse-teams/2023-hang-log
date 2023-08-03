import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export const sectionStyling = css({
  position: 'relative',
  width: '100%',
  padding: `${Theme.spacer.spacing4} 0`,
  marginBottom: Theme.spacer.spacing5,
});

export const titleStyling = css({
  marginTop: Theme.spacer.spacing3,
  marginBottom: Theme.spacer.spacing3,
});
