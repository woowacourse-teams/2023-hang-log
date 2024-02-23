import { css } from '@emotion/react';

export const tableStyling = css({
  'th:first-of-type, th:last-of-type': {
    width: '10%',
  },

  'th:not(:first-of-type):not(:last-of-type)': {
    width: '20%',
  },
});
