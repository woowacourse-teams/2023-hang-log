import { axiosInstance } from '@api/axiosInstance';

import type { NewTripData } from '@type/trips';

import { END_POINTS } from '@constants/api';

export const postTrip = async (newTripData: NewTripData) => {
  const response = await axiosInstance.post(END_POINTS.TRIPS, newTripData);

  const tripId = response.headers.location.replace(`${END_POINTS.TRIPS}/`, '');

  return tripId;
};
