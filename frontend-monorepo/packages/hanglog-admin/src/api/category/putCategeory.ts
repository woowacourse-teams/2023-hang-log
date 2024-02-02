import { axiosInstance } from '@api/axiosInstance';

import type { CategoryData } from '@type/category';

import { END_POINTS } from '@constants/api';

export const putCategory = (category: CategoryData) => {
  return axiosInstance.put<CategoryData>(END_POINTS.CHANGE_CATEGORY(category.id), {
    ...category,
  });
};
