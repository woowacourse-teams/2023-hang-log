import { css } from '@emotion/react';

import { Theme } from 'hang-log-design-system';

export const GlobalStyle = css({
  table: {
    width: '100%',
    borderCollapse: 'collapse',
    textAlign: 'left',
    fontFamily: 'sans-serif',
  },

  'th, td': {
    padding: Theme.spacer.spacing3,
    borderBottom: `1px solid ${Theme.color.gray500}`,
  },

  th: {
    backgroundColor: 'transparent',
    borderBottom: `1px solid ${Theme.color.blue500}`,
  },

  'tr:hover': {
    backgroundColor: Theme.color.gray200,
  },

  'th:first-of-type, td:first-of-type': {
    textAlign: 'center',
  },
});
