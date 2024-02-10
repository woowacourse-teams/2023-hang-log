import { axiosInstance } from '@api/axiosInstance';

import type { CategoryData } from '@type/category';

import { END_POINTS } from '@constants/api';

export const getCategory = async () => {
  const { data } = await axiosInstance.get<CategoryData[]>(END_POINTS.CATEGORY);
  return data;
};
