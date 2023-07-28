import { END_POINTS } from '@constants/api';
import { NewTripData } from '@type/trips';

import { axiosInstance } from '@api/axiosInstance';

export const postTrip = () => async (newTripData: NewTripData) => {
  const response = await axiosInstance.post(END_POINTS.TRIPS, newTripData);

  const tripId = response.headers.location.replace(`${END_POINTS.TRIPS}/`, '');

  return tripId;
};
