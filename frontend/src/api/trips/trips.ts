import { END_POINTS } from '@constants/api';
import type { NewTripData, TripsData } from '@type/trips';

import { axiosInstance } from '@api/axiosInstance';

export const getTrips = async () => {
  const { data } = await axiosInstance.get<{ trips: TripsData[] }>(END_POINTS.TRIPS);

  return data?.trips;
};

export const postNewTrip = async (newTripData: NewTripData) => {
  const response = await axiosInstance.post(END_POINTS.TRIPS, newTripData);

  return response.headers.location;
};
