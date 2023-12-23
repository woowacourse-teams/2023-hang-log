import { useQueryClient } from '@tanstack/react-query';

import type { TripData, TripTypeData } from '@type/trip';

export const useTrip = (tripType: TripTypeData, tripId: string) => {
  const queryClient = useQueryClient();
  const tripData = queryClient.getQueryData<TripData>([`${tripType}trip`, tripId])!;

  const dates = tripData.dayLogs.map((data) => ({
    id: data.id,
    date: data.date,
  }));

  return { tripData, dates };
};
