import { END_POINTS } from '@constants/api';
import type { CityData } from '@type/city';

import { axiosInstance } from '@api/axiosInstance';

export const getCities = async () => {
  const { data } = await axiosInstance.get<CityData[]>(END_POINTS.CITIES);

  return data;
};
