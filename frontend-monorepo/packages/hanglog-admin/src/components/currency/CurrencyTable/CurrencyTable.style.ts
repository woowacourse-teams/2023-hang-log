import { css } from '@emotion/react';

export const tableStyling = css({
  'th:first-of-type, th:last-of-type': {
    width: '5%',
  },

  'th:second-of-type': {
    width: '10%',
  },

  'th:not(:first-of-type):not(:last-of-type):not(:second-of-type)': {
    width: '8%',
  },
});
