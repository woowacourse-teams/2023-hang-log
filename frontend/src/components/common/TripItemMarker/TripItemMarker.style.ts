import { css } from '@emotion/react';
import { Theme } from 'hang-log-design-system';

export const getMarkerContainerStyling = (isZoomedOut: boolean) => {
  return css({
    position: 'relative',
    top: isZoomedOut ? Theme.spacer.spacing4 : Theme.spacer.spacing0,
  });
};

export const getLabelStyling = (isSelected: boolean) => {
  return css({
    color: isSelected ? '#D83AA2' : '#0083BB',
    fontSize: '15px',
    fontWeight: 600,

    '&::after': {
      content: 'attr(data-text)',

      position: 'absolute',
      left: 0,
      zIndex: -1,

      '-webkitTextStroke': '3px white',

      fontSize: '1em',
    },
  });
};
