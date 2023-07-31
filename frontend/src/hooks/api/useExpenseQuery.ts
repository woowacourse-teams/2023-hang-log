import { NETWORK } from '@/constants/api';
import { useQuery } from '@tanstack/react-query';
import type { ExpenseData } from '@type/expense';
import type { AxiosError } from 'axios';

import { getExpense } from '@api/expense/getExpense';

export const useExpenseQuery = (tripId: number) => {
  const { data } = useQuery<ExpenseData, AxiosError>(
    ['expense', tripId],
    () => getExpense(tripId),
    {
      retry: NETWORK.RETRY_COUNT,
      suspense: true,
      useErrorBoundary: true,
    }
  );

  return { expenseData: data! };
};
