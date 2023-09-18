import { axiosInstance } from '@api/axiosInstance';

import type { CommunityTripsData } from '@type/trips';

import { END_POINTS } from '@constants/api';

export const getCommunityTrips = async (page: number, size: number, isLoggedIn: boolean) => {
  const { data } = await axiosInstance.get<CommunityTripsData>(
    END_POINTS.COMMUNITY_TRIPS(page, size),
    {
      useAuth: isLoggedIn,
    }
  );
  return data;
};
