import { useQuery } from '@tanstack/react-query';

import type { AxiosError } from 'axios';

import { getExpenseCategory } from '@api/expense/getExpenseCategory';

import type { ExpenseCategoryData } from '@type/expense';

import { NETWORK } from '@constants/api';

export const useExpenseCategoryQuery = () => {
  const { data } = useQuery<ExpenseCategoryData[], AxiosError>(
    ['expenseCategory'],
    getExpenseCategory,
    {
      retry: NETWORK.RETRY_COUNT,
      suspense: true,
      useErrorBoundary: true,
      cacheTime: Infinity,
    }
  );

  return { expenseCategoryData: data! };
};
