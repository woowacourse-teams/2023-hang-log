import { END_POINTS } from '@constants/api';
import type { TripsData } from '@type/trips';

import { axiosInstance } from '@api/axiosInstance';

export const getTrips = async () => {
  const { data } = await axiosInstance.get<TripsData[] | []>(END_POINTS.TRIPS);

  return data;
};
