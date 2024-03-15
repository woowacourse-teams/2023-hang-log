import { Global } from '@emotion/react';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { HangLogProvider } from 'hang-log-design-system';
import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { RecoilRoot } from 'recoil';

import { GlobalStyle } from '@styles/index';

import AppRouter from '@router/AppRouter';

async function enableMocking() {
  if (process.env.NODE_ENV !== 'development') {
    return;
  }
  const { worker } = await import('./common/mocks/browser');
  return worker.start();
}

const root = createRoot(document.querySelector('#root') as Element);

const queryClient = new QueryClient();

enableMocking().then(() => {
  root.render(
    <StrictMode>
      <QueryClientProvider client={queryClient}>
        <RecoilRoot>
          <HangLogProvider>
            <Global styles={GlobalStyle} />
            <AppRouter />
          </HangLogProvider>
        </RecoilRoot>
      </QueryClientProvider>
    </StrictMode>
  );
});
