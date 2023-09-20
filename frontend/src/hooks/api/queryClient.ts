import { QueryClient } from '@tanstack/react-query';

import { NETWORK } from '@constants/api';

export const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      retry: NETWORK.RETRY_COUNT,
      suspense: true,
      useErrorBoundary: true,
    },
  },
});
