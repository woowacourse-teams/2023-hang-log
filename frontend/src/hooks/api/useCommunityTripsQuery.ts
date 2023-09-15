import { useQuery } from '@tanstack/react-query';

import type { AxiosError } from 'axios';

import { getCommunityTrips } from '@api/trips/getCommunityTrips';

import type { CommunityTripsData } from '@type/trips';

export const useCommunityTripsQuery = (page: number, size: number) => {
  const { data } = useQuery<CommunityTripsData, AxiosError>(['communityTrips', page], () =>
    getCommunityTrips(page, size)
  );

  return { tripsData: data! };
};
