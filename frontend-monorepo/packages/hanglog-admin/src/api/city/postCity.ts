import { axiosInstance } from '@api/axiosInstance';

import type { CityFormData } from '@type/city';

import { END_POINTS } from '@constants/api';

export const postCity = async (cityFormData: CityFormData) => {
  const response = await axiosInstance.post(END_POINTS.CITY, cityFormData);

  const tripId = response.headers.location.replace(`${END_POINTS.CITY}/`, '');

  return tripId;
};
