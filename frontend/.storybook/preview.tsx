import type { Preview } from '@storybook/react';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { HangLogProvider } from 'hang-log-design-system';
import { initialize, mswDecorator } from 'msw-storybook-addon';
import React from 'react';
import { MemoryRouter } from 'react-router-dom';
import { RecoilRoot } from 'recoil';

import { handlers } from '../src/mocks/handlers';

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

const queryClient = new QueryClient();

export const decorators = [
  (Story) => (
    <MemoryRouter initialEntries={['/']}>
      <QueryClientProvider client={queryClient}>
        <RecoilRoot>
          <HangLogProvider>
            <Story />
          </HangLogProvider>
        </RecoilRoot>
      </QueryClientProvider>
    </MemoryRouter>
  ),
  mswDecorator,
];
