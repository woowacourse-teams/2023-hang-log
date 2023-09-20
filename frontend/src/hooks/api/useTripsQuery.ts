import { useQuery } from '@tanstack/react-query';

import type { AxiosError } from 'axios';

import { getTrips } from '@api/trips/getTrips';

import type { TripsData } from '@type/trips';

export const useTripsQuery = () => {
  const { data } = useQuery<TripsData[], AxiosError>(['trips'], getTrips);

  return { tripsData: data! };
};
