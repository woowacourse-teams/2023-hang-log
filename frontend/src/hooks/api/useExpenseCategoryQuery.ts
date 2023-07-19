import { ExpenseCategoryData } from '@/types/expense';
import { NETWORK } from '@constants/api';
import { useQuery } from '@tanstack/react-query';
import type { AxiosError } from 'axios';

import { getExpenseCategory } from '@api/expense/getExpenseCategory';

export const useExpenseCategoryQuery = () => {
  const { data } = useQuery<ExpenseCategoryData[], AxiosError>(
    ['expenseCategory'],
    getExpenseCategory,
    {
      retry: NETWORK.RETRY_COUNT,
      useErrorBoundary: true,
    }
  );

  return { expenseCategoryData: data! };
};
