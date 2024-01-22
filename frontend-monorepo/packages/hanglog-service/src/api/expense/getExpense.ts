import { axiosInstance } from '@api/axiosInstance';

import type { ExpenseData } from '@type/expense';

import { END_POINTS } from '@constants/api';

export const getExpense = async (tripId: string) => {
  const { data } = await axiosInstance.get<ExpenseData>(END_POINTS.EXPENSE(tripId));

  return data;
};
