import { END_POINTS } from '@constants/api';
import type { TripItemFormData } from '@type/tripItem';

import { axiosInstance } from '@api/axiosInstance';

interface PutTripItemParams extends TripItemFormData {
  tripId: number;
  itemId: number;
}

export const putTripItem =
  () =>
  ({ tripId, itemId, ...information }: PutTripItemParams) => {
    return axiosInstance.put<TripItemFormData>(END_POINTS.CHANGE_TRIP_ITEM(tripId, itemId), {
      ...information,
    });
  };
