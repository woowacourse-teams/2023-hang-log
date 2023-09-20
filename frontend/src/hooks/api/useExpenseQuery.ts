import { getSharedExpense } from '@/api/expense/getSharedExpense';

import { useQuery } from '@tanstack/react-query';

import type { AxiosError } from 'axios';

import { getExpense } from '@api/expense/getExpense';

import type { ExpenseData } from '@type/expense';

export const useExpenseQuery = (tripId: number, isShared = false) => {
  const { data } = useQuery<ExpenseData, AxiosError>(
    ['expense', tripId],
    !isShared ? () => getExpense(tripId) : () => getSharedExpense(String(tripId))
  );

  return { expenseData: data! };
};
