import { END_POINTS } from '@constants/api';
import type { ExpenseCategoryData } from '@type/expense';

import { axiosInstance } from '@api/axiosInstance';

export const getExpenseCategory = async () => {
  const { data } = await axiosInstance.get<ExpenseCategoryData[]>(END_POINTS.EXPENSE_CATEGORY);

  return data;
};
