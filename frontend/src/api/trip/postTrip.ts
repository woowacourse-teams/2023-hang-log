import { END_POINTS } from '@constants/api';
import { NewTripData } from '@type/trips';

import { axiosInstance } from '@api/axiosInstance';

export const postTrip = () => async (newTripData: NewTripData) => {
  const response = await axiosInstance.post(END_POINTS.TRIPS, newTripData);

  return response.headers.location;
};
