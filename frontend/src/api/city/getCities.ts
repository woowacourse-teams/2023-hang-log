import { END_POINTS } from '@constants/api';

import { axiosInstance } from '@api/axiosInstance';

import type { City } from '@components/common/CitySearchBar/CitySearchBar';

export const getCities = async () => {
  const { data } = await axiosInstance.get<City[]>(END_POINTS.CITIES);
  return data;
};
