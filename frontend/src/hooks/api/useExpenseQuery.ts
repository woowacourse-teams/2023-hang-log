import { useQuery } from '@tanstack/react-query';

import type { AxiosError } from 'axios';

import { getCommunityTripExpense } from '@api/expense/getCommunityTripExpense';
import { getExpense } from '@api/expense/getExpense';
import { getSharedExpense } from '@api/expense/getSharedExpense';

import type { ExpenseData } from '@type/expense';

export const useExpenseQuery = (
  tripId: string,
  { isShared, isPublished }: { isShared: boolean; isPublished: boolean }
) => {
  let queryFn = () => getExpense(tripId);

  if (isPublished) {
    queryFn = () => getCommunityTripExpense(tripId);
  }

  if (isShared && !isPublished) {
    queryFn = () => getSharedExpense(tripId);
  }

  const { data } = useQuery<ExpenseData, AxiosError>(['expense', tripId], queryFn);

  return { expenseData: data! };
};
