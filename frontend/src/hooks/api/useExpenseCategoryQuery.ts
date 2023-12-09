import { useSuspenseQuery } from '@tanstack/react-query';

import type { AxiosError } from 'axios';

import { getExpenseCategory } from '@api/expense/getExpenseCategory';

import type { ExpenseCategoryData } from '@type/expense';

export const useExpenseCategoryQuery = () => {
  const { data: expenseCategoryData } = useSuspenseQuery<ExpenseCategoryData[], AxiosError>({
    queryKey: ['expenseCategory'],
    queryFn: getExpenseCategory,
  });

  return { expenseCategoryData };
};
