import { END_POINTS } from '@constants/api';
import type { TripFormData } from '@type/trip';

import { axiosInstance } from '@api/axiosInstance';

export interface PutTripParams extends TripFormData {
  tripId: number;
}

export const putTrip =
  () =>
  ({ tripId, ...tripInformation }: PutTripParams) => {
    return axiosInstance.put<TripFormData>(END_POINTS.TRIP(tripId), {
      ...tripInformation,
    });
  };
