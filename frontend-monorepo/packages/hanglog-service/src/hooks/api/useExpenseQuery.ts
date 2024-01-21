import { match } from 'ts-pattern';

import { useSuspenseQuery } from '@tanstack/react-query';

import type { AxiosError } from 'axios';

import { getCommunityTripExpense } from '@api/expense/getCommunityTripExpense';
import { getExpense } from '@api/expense/getExpense';
import { getSharedExpense } from '@api/expense/getSharedExpense';

import type { ExpenseData } from '@type/expense';
import type { TripTypeData } from '@type/trip';

import { TRIP_TYPE } from '@constants/trip';

export const useExpenseQuery = (tripId: string, tripType: TripTypeData) => {
  const queryFn = {
    expense: () => getExpense(tripId),
  };

  if (tripType === TRIP_TYPE.PUBLISHED) {
    queryFn.expense = () => getCommunityTripExpense(tripId);
  }

  if (tripType === TRIP_TYPE.SHARED) {
    queryFn.expense = () => getSharedExpense(tripId);
  }

  const { data: expenseData } = useSuspenseQuery<ExpenseData, AxiosError>({
    queryKey: [tripType, 'expense', tripId],
    queryFn: match(tripType)
      .with(TRIP_TYPE.PUBLISHED, () => () => getCommunityTripExpense(tripId))
      .with(TRIP_TYPE.SHARED, () => () => getSharedExpense(tripId))
      .otherwise(() => () => getExpense(tripId)),
    gcTime: 5 * 60 * 1000,
    staleTime: 60 * 1000,
  });

  return { expenseData };
};
