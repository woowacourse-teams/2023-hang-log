import { axiosInstance } from '@api/axiosInstance';

import type { TripItemFormData } from '@type/tripItem';

import { END_POINTS } from '@constants/api';

interface PutTripItemParams extends TripItemFormData {
  tripId: number;
  itemId: number;
}

export const putTripItem = ({ tripId, itemId, ...information }: PutTripItemParams) => {
  return axiosInstance.put<TripItemFormData>(END_POINTS.CHANGE_TRIP_ITEM(tripId, itemId), {
    ...information,
  });
};
