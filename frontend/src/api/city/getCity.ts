import { axiosInstance } from '@api/axiosInstance';

import type { CityData } from '@type/city';

import { END_POINTS } from '@constants/api';

export const getCity = async () => {
  const { data } = await axiosInstance.get<CityData[]>(END_POINTS.CITY);

  return data;
};
