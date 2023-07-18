import { END_POINTS } from '@constants/api';
import type { TripData } from '@type/trip';

import { axiosInstance } from '@api/axiosInstance';

export const getTrip = async (tripId: number) => {
  const { data } = await axiosInstance.get<TripData>(END_POINTS.TRIP(tripId));

  return data;
};
