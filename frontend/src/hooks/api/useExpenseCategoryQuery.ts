import { useQuery } from '@tanstack/react-query';

import type { AxiosError } from 'axios';

import { getExpenseCategory } from '@api/expense/getExpenseCategory';

import type { ExpenseCategoryData } from '@type/expense';

export const useExpenseCategoryQuery = () => {
  const { data } = useQuery<ExpenseCategoryData[], AxiosError>(
    ['expenseCategory'],
    getExpenseCategory
  );

  return { expenseCategoryData: data! };
};
