import { axiosInstance } from '@api/axiosInstance';

import type { TripsData } from '@type/trips';

import { END_POINTS } from '@constants/api';

export const getTrips = async () => {
  const { data } = await axiosInstance.get<TripsData[]>(END_POINTS.TRIPS);

  return data;
};
