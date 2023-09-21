import { axiosInstance } from '@api/axiosInstance';

import type { TripItemFormData } from '@type/tripItem';

import { END_POINTS } from '@constants/api';

export interface PostTripItemParams extends TripItemFormData {
  tripId: string;
}

export const postTripItem = ({ tripId, ...information }: PostTripItemParams) => {
  return axiosInstance.post<TripItemFormData>(END_POINTS.CREATE_TRIP_ITEM(tripId), {
    ...information,
  });
};
