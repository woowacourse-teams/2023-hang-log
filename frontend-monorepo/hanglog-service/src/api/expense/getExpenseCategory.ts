import { axiosInstance } from '@api/axiosInstance';

import type { ExpenseCategoryData } from '@type/expense';

import { END_POINTS } from '@constants/api';

export const getExpenseCategory = async () => {
  const { data } = await axiosInstance.get<ExpenseCategoryData[]>(END_POINTS.EXPENSE_CATEGORY);

  return data;
};
