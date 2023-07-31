import { END_POINTS } from '@constants/api';
import type { ExpenseData } from '@type/expense';

import { axiosInstance } from '@api/axiosInstance';

export const getExpense = async (tripId: number) => {
  const { data } = await axiosInstance.get<ExpenseData>(END_POINTS.EXPENSE(tripId));

  return data;
};
