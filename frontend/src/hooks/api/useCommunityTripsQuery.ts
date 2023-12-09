import { useSuspenseQuery } from '@tanstack/react-query';

import type { AxiosError } from 'axios';

import { getCommunityTrips } from '@api/trips/getCommunityTrips';

import type { CommunityTripsData } from '@type/trips';

export const useCommunityTripsQuery = (page: number, size: number, isLoggedIn: boolean) => {
  const { data: communityTripsData } = useSuspenseQuery<CommunityTripsData, AxiosError>({
    queryKey: ['communityTrips', page, size, isLoggedIn],
    queryFn: () => getCommunityTrips(page, size, isLoggedIn),
  });

  return { communityTripsData };
};
