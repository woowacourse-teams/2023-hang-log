import { Global } from '@emotion/react';

import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';

import { QueryClientProvider } from '@tanstack/react-query';
import { ReactQueryDevtools } from '@tanstack/react-query-devtools';

import { RecoilRoot } from 'recoil';

import { HangLogProvider } from 'hang-log-design-system';

import HttpsRedirect from '@components/utils/HttpsRedirect';

import { queryClient } from '@hooks/api/queryClient';

import AppRouter from '@router/AppRouter';

import { GlobalStyle } from '@styles/index';

import { worker } from '@mocks/browser';

const main = async () => {
  if (process.env.NODE_ENV === 'development') {
    await worker.start({
      serviceWorker: {
        url: '/mockServiceWorker.js',
      },
      onUnhandledRequest: 'bypass',
    });
  }

  const root = createRoot(document.querySelector('#root') as Element);

  root.render(
    <StrictMode>
      <HttpsRedirect>
        <QueryClientProvider client={queryClient}>
          <RecoilRoot>
            <HangLogProvider>
              <Global styles={GlobalStyle} />
              <AppRouter />
            </HangLogProvider>
          </RecoilRoot>
          <ReactQueryDevtools initialIsOpen={false} />
        </QueryClientProvider>
      </HttpsRedirect>
    </StrictMode>
  );
};

main();
