import { css } from '@emotion/react';

export interface subTitleStylingProps {
  topPadding: number;
  bottomPadding: number;
}

export const subTitleStyling = ({ topPadding = 0, bottomPadding = 0 }: subTitleStylingProps) => {
  return css({
    paddingTop: `${topPadding}px`,
    paddingLeft: '50px',
    paddingBottom: `${bottomPadding}px`,

    fontWeight: 600,
  });
};
