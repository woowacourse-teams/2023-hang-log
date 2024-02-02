import { axiosInstance } from '@api/axiosInstance';

import type { CategoryData } from '@/types/category';

import { END_POINTS } from '@constants/api';

export const postCategory = async (categoryData: CategoryData) => {
  const response = await axiosInstance.post(END_POINTS.CATEGORY, categoryData);

  const categoryId = response.headers.location.replace(`${END_POINTS.CATEGORY}/`, '');

  return categoryId;
};
