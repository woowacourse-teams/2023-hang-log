import { useQuery } from '@tanstack/react-query';

import type { AxiosError } from 'axios';

import { getRecommendedTrips } from '@api/trips/getRecommendedTrips';

import type { RecommendedTripsData } from '@type/trips';

export const useRecommendedTripsQuery = () => {
  const { data } = useQuery<RecommendedTripsData, AxiosError>(
    ['recommendedTrips'],
    getRecommendedTrips
  );

  return { tripsData: data! };
};
