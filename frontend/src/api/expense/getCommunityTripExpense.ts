import { axiosInstance } from '@api/axiosInstance';

import type { ExpenseData } from '@type/expense';

import { END_POINTS } from '@constants/api';

export const getCommunityTripExpense = async (tripId: string): Promise<ExpenseData> => {
  const { data } = await axiosInstance.get<ExpenseData>(END_POINTS.COMMUNITY_EXPENSE(tripId));

  return data;
};
