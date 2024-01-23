import { css } from '@emotion/react';

import { Theme } from 'hang-log-design-system';

export const GlobalStyle = css({
  // Places API - Autocomplete Widget 스타일링
  '.pac-container': {
    borderTop: 'none',
    borderRadius: Theme.borderRadius.medium,

    width: '100%',
    marginTop: Theme.spacer.spacing2,
    padding: `12px 0`,

    boxShadow: Theme.boxShadow.shadow8,

    '& > .pac-item:first-of-type': {
      borderTop: 'none',
    },
  },

  '.pac-item': {
    display: 'flex',
    alignItems: 'center',
    gap: Theme.spacer.spacing1,

    border: 'none',
    padding: `${Theme.spacer.spacing1} ${Theme.spacer.spacing3}`,

    '&:hover': {
      backgroundColor: Theme.color.gray100,
    },

    '& > .pac-icon': {
      display: 'none',
    },
  },
});
