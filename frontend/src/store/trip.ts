import { useQueryClient } from '@tanstack/react-query';
import type { TripData } from '@type/trip';
import { selectorFamily } from 'recoil';

export const tripDatesState = selectorFamily({
  key: 'tripDates',
  get: (tripId) => () => {
    const queryClient = useQueryClient();

    const tripData = queryClient.getQueryData<TripData>(['trip', tripId])!;

    return tripData.dayLogs.map((data) => ({
      id: data.id,
      date: data.date,
    }));
  },
});
