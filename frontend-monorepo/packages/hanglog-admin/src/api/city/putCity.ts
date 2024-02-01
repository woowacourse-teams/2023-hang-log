import { axiosInstance } from '@api/axiosInstance';

import type { CityFormData } from '@type/city';

import { END_POINTS } from '@constants/api';

export interface PutCityParams extends CityFormData {
  cityId: number;
}

export const putCity = ({ cityId, ...cityInformation }: PutCityParams) => {
  return axiosInstance.put<CityFormData>(END_POINTS.CHANGE_CITY(cityId), {
    ...cityInformation,
  });
};
