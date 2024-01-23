import { useSuspenseQuery } from '@tanstack/react-query';

import type { AxiosError } from 'axios';

import { getRecommendedTrips } from '@api/trips/getRecommendedTrips';

import type { RecommendedTripsData } from '@type/trips';

export const useRecommendedTripsQuery = (isLoggedIn: boolean) => {
  const { data: recommendedTripsData } = useSuspenseQuery<RecommendedTripsData, AxiosError>({
    queryKey: ['recommendedTrips', isLoggedIn],
    queryFn: () => getRecommendedTrips(isLoggedIn),
  });

  return { recommendedTripsData };
};
