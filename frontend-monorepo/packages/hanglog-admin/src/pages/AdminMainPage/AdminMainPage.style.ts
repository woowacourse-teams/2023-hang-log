import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export interface subTitleStylingProps {
  topPadding: number;
  bottomPadding: number;
}

export const subTitleStyling = ({ topPadding = 0, bottomPadding = 0 }: subTitleStylingProps) => {
  return css({
    paddingTop: `${topPadding}px`,
    paddingLeft: Theme.spacer.spacing6,
    paddingBottom: `${bottomPadding}px`,

    fontWeight: 600,
  });
};
