import { END_POINTS } from '@constants/api';
import { TripPutData } from '@type/trip';

import { axiosInstance } from '@api/axiosInstance';

export interface PutTripParams extends TripPutData {
  tripId: number;
}

export const putTrip =
  () =>
  ({ tripId, ...tripInformation }: PutTripParams) => {
    return axiosInstance.put<TripPutData>(END_POINTS.TRIP(tripId), {
      ...tripInformation,
    });
  };
