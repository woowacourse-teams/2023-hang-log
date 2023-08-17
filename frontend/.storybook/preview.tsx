import type { Preview } from '@storybook/react';

import React, { Suspense } from 'react';
import { MemoryRouter } from 'react-router-dom';

import { QueryClient, QueryClientProvider } from '@tanstack/react-query';

import { RecoilRoot } from 'recoil';

import { initialize, mswDecorator } from 'msw-storybook-addon';

import { HangLogProvider, Spinner } from 'hang-log-design-system';

import { axiosInstance } from '../src/api/axiosInstance';
import { ACCESS_TOKEN_KEY } from '../src/constants/api';
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

const localStorageResetDecorator = (Story) => {
  window.localStorage.clear();
  window.localStorage.setItem(ACCESS_TOKEN_KEY, 'hanglogAccessToken');

  return <Story />;
};

export const decorators = [
  (Story) => (
    <MemoryRouter initialEntries={['/']}>
      <QueryClientProvider client={queryClient}>
        <RecoilRoot>
          <HangLogProvider>
            <Suspense fallback={<Spinner />}>
              <Story />
            </Suspense>
          </HangLogProvider>
        </RecoilRoot>
      </QueryClientProvider>
    </MemoryRouter>
  ),
  localStorageResetDecorator,
  mswDecorator,
];
