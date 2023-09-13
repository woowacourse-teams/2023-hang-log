import { axiosInstance } from '@api/axiosInstance';

import type { RecommendedTripsData } from '@type/trips';

import { END_POINTS } from '@constants/api';

export const getRecommendedTrips = async () => {
  const { data } = await axiosInstance.get<RecommendedTripsData>(END_POINTS.RECOMMENDED_TRIPS);

  return data;
};
