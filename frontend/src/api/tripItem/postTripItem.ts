import { END_POINTS } from '@constants/api';
import type { TripItemFormData } from '@type/tripItem';

import { axiosInstance } from '@api/axiosInstance';

export interface PostTripItemParams extends TripItemFormData {
  tripId: number;
}

export const postTripItem =
  () =>
  ({ tripId, ...information }: PostTripItemParams) => {
    return axiosInstance.post<TripItemFormData>(END_POINTS.CREATE_TRIP_ITEM(tripId), {
      ...information,
    });
  };
