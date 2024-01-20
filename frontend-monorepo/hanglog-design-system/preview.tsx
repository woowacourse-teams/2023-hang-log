import { Global, ThemeProvider } from '@emotion/react';
import type { Preview } from '@storybook/react';
import React from 'react';

import { GlobalStyle } from '../src/styles/GlobalStyle';
import { Theme } from '../src/styles/Theme';

const preview: Preview = {
  parameters: {
    actions: { argTypesRegex: '^on[A-Z].*' },
    controls: {
      matchers: {
        color: /(background|color)$/i,
        date: /Date$/,
      },
    },
  },
};

export default preview;

export const decorators = [
  (Story) => (
    <ThemeProvider theme={Theme}>
      <Global styles={GlobalStyle} />
      <Story />
    </ThemeProvider>
  ),
];
