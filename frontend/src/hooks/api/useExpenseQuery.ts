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
  const queryFn = {
    expense: () => getExpense(tripId),
  };

  if (isPublished) {
    queryFn.expense = () => getCommunityTripExpense(tripId);
  }

  if (isShared && !isPublished) {
    queryFn.expense = () => getSharedExpense(tripId);
  }

  const { data } = useQuery<ExpenseData, AxiosError>(['expense', tripId], queryFn.expense);

  return { expenseData: data! };
};
