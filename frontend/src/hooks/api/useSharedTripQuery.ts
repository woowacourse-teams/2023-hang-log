import { useQuery } from '@tanstack/react-query';

import type { AxiosError } from 'axios';

import { getSharedTrip } from '@api/trip/getSharedTrip';

import type { TripData } from '@type/trip';

export const useSharedTripQuery = (tripId: string) => {
  const { data } = useQuery<TripData, AxiosError>(['SHARED', tripId], () => getSharedTrip(tripId));

  return { tripData: data! };
};
