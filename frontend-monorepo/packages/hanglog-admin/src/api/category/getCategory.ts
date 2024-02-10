import type { CategoryData } from '@type/category';

import { END_POINTS } from '@constants/api';

import { axiosInstance } from '@api/axiosInstance';

export const getCategory = async () => {
  const { data } = await axiosInstance.get<CategoryData[]>(END_POINTS.CATEGORY);
  return data;
};
