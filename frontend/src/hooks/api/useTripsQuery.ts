import { NETWORK } from '@constants/api';
import { useQuery } from '@tanstack/react-query';
import type { TripsData } from '@type/trips';
import type { AxiosError } from 'axios';

import { getTrips } from '@api/trips/getTrips';

export const useTripsQuery = () => {
  const { data } = useQuery<TripsData[], AxiosError>(['trips'], getTrips, {
    retry: NETWORK.RETRY_COUNT,
    suspense: true,
    useErrorBoundary: true,
  });

  return { tripsData: data! };
};
