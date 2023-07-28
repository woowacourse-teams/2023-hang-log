import { END_POINTS } from '@constants/api';

import { axiosInstance } from '@api/axiosInstance';

interface DeleteTripItemParams {
  tripId: number;
  itemId: number;
}

export const deleteTripItem =
  () =>
  ({ tripId, itemId }: DeleteTripItemParams) => {
    return axiosInstance.delete(END_POINTS.CHANGE_TRIP_ITEM(tripId, itemId));
  };
