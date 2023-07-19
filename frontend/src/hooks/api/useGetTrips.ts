import { NETWORK } from '@constants/api';
import { useQuery } from '@tanstack/react-query';
import type { TripsData } from '@type/trips';
import { AxiosError } from 'axios';

import { getTrips } from '@api/trips/trips';

export const useGetTrips = () => {
  const { data: tripsData } = useQuery<TripsData[], AxiosError>(['trips'], getTrips, {
    retry: NETWORK.RETRY_COUNT,
    suspense: true,
    useErrorBoundary: true,
  });

  return { tripsData };
};
