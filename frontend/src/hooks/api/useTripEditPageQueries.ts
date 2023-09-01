import { useQueries } from '@tanstack/react-query';

import { getExpenseCategory } from '@api/expense/getExpenseCategory';
import { getTrip } from '@api/trip/getTrip';

export const useTripEditPageQueries = (tripId: number) => {
  const [tripQuery, expenseCategoryQuery] = useQueries({
    queries: [
      { queryKey: ['trip', tripId], queryFn: () => getTrip(tripId) },
      { queryKey: ['expenseCategory'], queryFn: getExpenseCategory, staleTime: Infinity },
    ],
  });

  return { tripData: tripQuery.data!, expenseCategoryData: expenseCategoryQuery.data! };
};
