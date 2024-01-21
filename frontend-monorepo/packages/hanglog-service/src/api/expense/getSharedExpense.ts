import { axiosInstance } from '@api/axiosInstance';

import type { ExpenseData } from '@type/expense';

import { END_POINTS } from '@constants/api';

export const getSharedExpense = async (tripId: string) => {
  const { data } = await axiosInstance.get<ExpenseData>(END_POINTS.SHARED_EXPENSE(tripId), {
    useAuth: false,
    withCredentials: false,
  });

  return data;
};
