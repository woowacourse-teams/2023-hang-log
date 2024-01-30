import AppRouter from '@router/AppRouter';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { HangLogProvider } from 'hang-log-design-system';
import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { RecoilRoot } from 'recoil';

if (process.env.NODE_ENV === 'development') {
  import('./mocks/browser').then(({ worker }) => {
    worker.start();
  });
}

const root = createRoot(document.querySelector('#root') as Element);

const queryClient = new QueryClient();

root.render(
  <StrictMode>
    <QueryClientProvider client={queryClient}>
      <RecoilRoot>
        <HangLogProvider>
          <AppRouter />
        </HangLogProvider>
      </RecoilRoot>
    </QueryClientProvider>
  </StrictMode>
);
