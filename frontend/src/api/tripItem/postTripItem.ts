import { END_POINTS } from '@/constants/api';
import type { TripItemFormType } from '@type/tripItem';

import { axiosInstance } from '@api/axiosInstance';

export interface PostTripItemParams extends TripItemFormType {
  tripId: number;
}

export const postTripItem =
  () =>
  ({ tripId, ...information }: PostTripItemParams) => {
    return axiosInstance.post<TripItemFormType>(END_POINTS.CREATE_TRIP_ITEM(tripId), {
      ...information,
    });
  };
