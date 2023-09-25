import { useQuery } from '@tanstack/react-query';

import type { AxiosError } from 'axios';

import { getSharedTrip } from '@api/trip/getSharedTrip';

import type { TripData } from '@type/trip';

export const useSharedQuery = (tripId: string) => {
  const { data } = useQuery<TripData, AxiosError>(['trip', tripId], () => getSharedTrip(tripId));

  return { tripData: data! };
};
