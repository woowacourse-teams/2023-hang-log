import { axiosInstance } from '@api/axiosInstance';

import { convertImageURLToName } from '@utils/convertImageURLToName';

import type { TripFormData } from '@type/trip';

import { END_POINTS } from '@constants/api';

export interface PutTripParams extends TripFormData {
  tripId: string;
}

export const putTrip = ({ tripId, ...tripInformation }: PutTripParams) => {
  return axiosInstance.put<TripFormData>(END_POINTS.TRIP(tripId), {
    ...tripInformation,
    tripImage: convertImageURLToName(tripInformation.imageName),
  });
};
