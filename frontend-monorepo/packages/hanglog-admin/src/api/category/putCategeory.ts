import { axiosInstance } from '@api/axiosInstance';

import type { CategoryData } from '@type/category';

import { END_POINTS } from '@constants/api';

export const putCity = (category: CategoryData) => {
  return axiosInstance.put<CategoryData>(END_POINTS.CHANGE_CATEGORY(category.id), {
    ...category,
  });
};
