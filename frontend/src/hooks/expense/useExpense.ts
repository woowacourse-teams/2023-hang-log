import { useQueryClient } from '@tanstack/react-query';
import type { ExpenseData } from '@type/expense';
import { useMemo } from 'react';

import { getItemsByCategory } from '@utils/expense';

export const useExpense = (tripId: number) => {
  const queryClient = useQueryClient();

  const expenseData = queryClient.getQueryData<ExpenseData>(['expense', tripId])!;

  const dates = expenseData.dayLogs.map((data) => ({
    id: data.id,
    date: data.date,
  }));

  const categoryExpenseData = useMemo(() => getItemsByCategory(expenseData), [expenseData]);

  return { expenseData, dates, categoryExpenseData };
};
