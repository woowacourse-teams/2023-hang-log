import { useQuery } from '@tanstack/react-query';

import type { AxiosError } from 'axios';

import { getRecommendedTrips } from '@api/trips/getRecommendedTrips';

import type { RecommendedTripsData } from '@type/trips';

export const useRecommendedTripsQuery = (isLoggedIn: boolean) => {
  const { data } = useQuery<RecommendedTripsData, AxiosError>(
    ['recommendedTrips'],
    () => getRecommendedTrips(isLoggedIn),
    {
      cacheTime: 0,
    }
  );

  return { tripsData: data! };
};
