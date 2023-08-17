import { useQuery } from '@tanstack/react-query';

import type { AxiosError } from 'axios';

import { getTrips } from '@api/trips/getTrips';

import type { TripsData } from '@type/trips';

import { NETWORK } from '@constants/api';

export const useTripsQuery = () => {
  const { data } = useQuery<TripsData[], AxiosError>(['trips'], getTrips, {
    retry: NETWORK.RETRY_COUNT,
    suspense: true,
    useErrorBoundary: true,
  });

  return { tripsData: data! };
};
