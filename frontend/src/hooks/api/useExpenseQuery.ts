import { useQuery } from '@tanstack/react-query';

import type { AxiosError } from 'axios';

import { getExpense } from '@api/expense/getExpense';

import type { ExpenseData } from '@type/expense';

export const useExpenseQuery = (tripId: number) => {
  const { data } = useQuery<ExpenseData, AxiosError>(['expense', tripId], () => getExpense(tripId));

  return { expenseData: data! };
};
