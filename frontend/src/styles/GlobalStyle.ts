import { css } from '@emotion/react';

import { Theme } from '@styles/Theme';

export const GlobalStyle = css`
  * {
    padding: 0;
    margin: 0;
    box-sizing: border-box;
  }

  ul,
  ol,
  li {
    list-style: none;
  }

  html,
  body {
    font-family: system-ui, -apple-system, BlinkMacSystemFont, 'Open Sans', 'Helvetica Neue',
      sans-serif;
    font-size: 16px;
    color: ${Theme.color.gray800};
  }

  a {
    text-decoration: none;
    color: ${Theme.color.blue700};
  }
`;
