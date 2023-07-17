import { NETWORK } from '@constants/api';
import { useQuery } from '@tanstack/react-query';
import type { TripData } from '@type/trip';
import { AxiosError } from 'axios';

import { getTrip } from '@api/trip/getTrip';

export const useTripQuery = (tripId: number) => {
  const { data: tripData } = useQuery<TripData, AxiosError>(
    ['trip', tripId],
    () => getTrip(tripId),
    {
      retry: NETWORK.RETRY_COUNT,
      suspense: true,
      useErrorBoundary: true,
    }
  );

  return { tripData };
};
