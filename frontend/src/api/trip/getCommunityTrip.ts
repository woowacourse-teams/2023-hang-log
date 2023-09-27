import { axiosInstance } from '@api/axiosInstance';

import type { CommunityTripData } from '@type/trip';

import { END_POINTS } from '@constants/api';

export const getCommunityTrip = async (tripId: string) => {
  const { data } = await axiosInstance.get<CommunityTripData>(END_POINTS.COMMUNITY_TRIP(tripId), {
    useAuth: false,
  });

  return data;
};
