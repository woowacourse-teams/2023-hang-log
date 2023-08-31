import { useQueryClient } from '@tanstack/react-query';

import type { TripData } from '@type/trip';

export const useTripDates = (tripId: number) => {
  const queryClient = useQueryClient();

  const tripData = queryClient.getQueryData<TripData>(['trip', tripId])!;

  const dates = tripData.dayLogs.map((data) => ({
    id: data.id,
    date: data.date,
  }));

  return { dates };
};
