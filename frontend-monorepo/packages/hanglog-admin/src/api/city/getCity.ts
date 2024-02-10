import type { CityData } from '@type/city';

import { END_POINTS } from '@constants/api';

import { axiosInstance } from '@api/axiosInstance';

export const getCity = async () => {
  const { data } = await axiosInstance.get<CityData[]>(END_POINTS.CITY);
  return data;
};
