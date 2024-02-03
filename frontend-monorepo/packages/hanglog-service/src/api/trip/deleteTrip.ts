import { axiosInstance } from '@api/axiosInstance';

import { END_POINTS } from '@constants/api';

interface DeleteTripParams {
  tripId: string;
}

export const deleteTrip = ({ tripId }: DeleteTripParams) => {
  return axiosInstance.delete(END_POINTS.TRIP(tripId));
};
