import { getSharedTrip } from '@/api/trip/getSharedTrip';

import { useQuery } from '@tanstack/react-query';

import type { AxiosError } from 'axios';

import { getTrip } from '@api/trip/getTrip';

import type { TripData } from '@type/trip';

export const useTripQuery = (tripId: string, isShared: boolean = false) => {
  const { data } = useQuery<TripData, AxiosError>(
    ['trip', tripId],
    isShared ? () => getSharedTrip(tripId) : () => getTrip(tripId)
  );

  return { tripData: data! };
};
