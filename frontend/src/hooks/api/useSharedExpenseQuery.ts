import { useQuery } from '@tanstack/react-query';

import type { AxiosError } from 'axios';

import { getSharedExpense } from '@api/expense/getSharedExpense';

import type { ExpenseData } from '@type/expense';

export const useSharedExpenseQuery = (code: string) => {
  const { data } = useQuery<ExpenseData, AxiosError>(['expense', Number(code)], () =>
    getSharedExpense(code)
  );

  return { expenseData: data! };
};
