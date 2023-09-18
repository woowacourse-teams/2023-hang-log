import { useQuery } from '@tanstack/react-query';

import type { AxiosError } from 'axios';

import { getCommunityTrips } from '@api/trips/getCommunityTrips';

import type { CommunityTripsData } from '@type/trips';

export const useCommunityTripsQuery = (page: number, size: number, isLoggedIn: boolean) => {
  const { data } = useQuery<CommunityTripsData, AxiosError>(['communityTrips', page], () =>
    getCommunityTrips(page, size, isLoggedIn)
  );

  return { tripsData: data! };
};
