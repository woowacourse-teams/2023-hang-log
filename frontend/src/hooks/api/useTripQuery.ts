import { useQuery } from '@tanstack/react-query';

import type { AxiosError } from 'axios';

import { getTrip } from '@api/trip/getTrip';

import type { TripData } from '@type/trip';

import { NETWORK } from '@constants/api';

export const useTripQuery = (tripId: number) => {
  const { data } = useQuery<TripData, AxiosError>(['trip', tripId], () => getTrip(tripId), {
    retry: NETWORK.RETRY_COUNT,
    suspense: true,
    useErrorBoundary: true,
  });

  return { tripData: data! };
};
