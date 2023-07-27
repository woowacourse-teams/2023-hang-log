import { useQueryClient } from '@tanstack/react-query';
import { TripData } from '@type/trip';

export const useTripDates = (tripId: number) => {
  const queryClient = useQueryClient();

  const tripData = queryClient.getQueryData<TripData>(['trip', tripId])!;

  const tripDates = tripData.dayLogs.map((data) => ({
    id: data.id,
    date: data.date,
  }));

  return { tripDates };
};
