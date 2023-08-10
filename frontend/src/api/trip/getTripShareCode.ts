import { END_POINTS } from '@constants/api';
import type { TripSharedCodeData } from '@type/trip';

import { axiosInstance } from '@api/axiosInstance';

export const getTripShareCode = async (tripId: number) => {
  const { data } = await axiosInstance.get<TripSharedCodeData>(END_POINTS.SHARE(tripId));

  return data;
};
