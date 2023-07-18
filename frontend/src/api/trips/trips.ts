import { END_POINTS } from '@constants/api';
import type { TripsType } from '@type/trips';

import { axiosInstance } from '@api/axiosInstance';

export const getTrips = async () => {
  const { data } = await axiosInstance.get<{ trips: TripsType[] }>(END_POINTS.TRIPS);

  return data?.trips;
};
