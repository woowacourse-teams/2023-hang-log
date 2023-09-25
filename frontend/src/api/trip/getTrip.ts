import { axiosInstance } from '@api/axiosInstance';

import type { TripData } from '@type/trip';

import { END_POINTS } from '@constants/api';

export const getTrip = async (tripId: string) => {
  const { data } = await axiosInstance.get<TripData>(END_POINTS.TRIP(tripId));

  return data;
};
