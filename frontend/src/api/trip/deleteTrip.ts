import { END_POINTS } from '@constants/api';

import { axiosInstance } from '@api/axiosInstance';

interface DeleteTripParams {
  tripId: number;
}

export const deleteTrip = ({ tripId }: DeleteTripParams) => {
  return axiosInstance.delete(END_POINTS.TRIP(tripId));
};
