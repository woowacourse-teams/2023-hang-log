import { axiosInstance } from '@api/axiosInstance';

import type { TripData } from '@type/trip';

import { END_POINTS } from '@constants/api';

export const getCommunityTrip = async (tripId: string, isLoggedIn: boolean) => {
  const { data } = await axiosInstance.get<TripData>(END_POINTS.COMMUNITY_TRIP(tripId), {
    useAuth: isLoggedIn,
  });

  return data;
};
