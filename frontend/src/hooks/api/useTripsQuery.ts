import { useSuspenseQuery } from '@tanstack/react-query';

import type { AxiosError } from 'axios';

import { getTrips } from '@api/trips/getTrips';

import type { TripsData } from '@type/trips';

export const useTripsQuery = () => {
  const { data: tripsData } = useSuspenseQuery<TripsData[], AxiosError>({
    queryKey: ['trips'],
    queryFn: getTrips,
  });

  return { tripsData };
};
