import { axiosInstance } from '@api/axiosInstance';

import type { TripData } from '@type/trip';

import { END_POINTS } from '@constants/api';

export const getSharedTrip = async (code: string) => {
  const { data } = await axiosInstance.get<TripData>(END_POINTS.SHARED_TRIP(code), {
    useAuth: false,
    withCredentials: false,
  });

  return data;
};
