import { useSuspenseQueries } from '@tanstack/react-query';

import { getExpenseCategory } from '@api/expense/getExpenseCategory';
import { getTrip } from '@api/trip/getTrip';

import { TRIP_TYPE } from '@constants/trip';

export const useTripEditPageQueries = (tripId: string) => {
  const [{ data: tripData }, { data: expenseCategoryData }] = useSuspenseQueries({
    queries: [
      { queryKey: [TRIP_TYPE.PERSONAL, tripId], queryFn: () => getTrip(tripId) },
      { queryKey: ['expenseCategory'], queryFn: getExpenseCategory, staleTime: Infinity },
    ],
  });

  return { tripData, expenseCategoryData };
};
