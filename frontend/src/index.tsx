import App from '@/App';
import { Global, ThemeProvider } from '@emotion/react';
import AppRouter from '@router/AppRouter';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { RecoilRoot } from 'recoil';

import { GlobalStyle } from '@styles/GlobalStyle';
import { Theme } from '@styles/Theme';

if (process.env.NODE_ENV === 'development') {
  const { worker } = require('./mocks/browser');

  worker.start();
}

const root = createRoot(document.querySelector('#root') as Element);

const queryClient = new QueryClient();

root.render(
  <StrictMode>
    <QueryClientProvider client={queryClient}>
      <RecoilRoot>
        <ThemeProvider theme={Theme}>
          <Global styles={GlobalStyle} />
          <AppRouter />
        </ThemeProvider>
      </RecoilRoot>
    </QueryClientProvider>
  </StrictMode>
);
