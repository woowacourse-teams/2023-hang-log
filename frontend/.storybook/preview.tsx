import { Global, ThemeProvider } from '@emotion/react';
import type { Preview } from '@storybook/react';
import { initialize, mswDecorator } from 'msw-storybook-addon';
import React from 'react';
import { MemoryRouter } from 'react-router-dom';
import { RecoilRoot } from 'recoil';

import { handlers } from '../src/mocks/handlers';
import { GlobalStyle } from '../src/styles/GlobalStyle';
import { Theme } from '../src/styles/Theme';

initialize();

const customViewports = {
  Default: {
    name: 'Default',
    styles: {
      width: '1512px',
      height: '982px',
    },
  },
};

const preview: Preview = {
  parameters: {
    actions: { argTypesRegex: '^on[A-Z].*' },
    controls: {
      matchers: {
        color: /(background|color)$/i,
        date: /Date$/,
      },
    },
    viewport: {
      viewports: { ...customViewports },
      defaultViewport: 'Default',
    },
    msw: {
      handlers: [...handlers],
    },
  },
};

export default preview;

export const decorators = [
  (Story) => (
    <MemoryRouter initialEntries={['/']}>
      <RecoilRoot>
        <ThemeProvider theme={Theme}>
          <Global styles={GlobalStyle} />
          <Story />
        </ThemeProvider>
      </RecoilRoot>
    </MemoryRouter>
  ),
  mswDecorator,
];
