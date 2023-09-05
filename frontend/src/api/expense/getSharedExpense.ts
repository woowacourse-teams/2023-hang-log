import { axiosInstance } from '@api/axiosInstance';

import type { ExpenseData } from '@type/expense';

import { END_POINTS } from '@constants/api';

export const getSharedExpense = async (code: string) => {
  const { data } = await axiosInstance.get<ExpenseData>(END_POINTS.SHARED_EXPENSE(code), {
    useAuth: false,
    withCredentials: false,
  });

  return data;
};
