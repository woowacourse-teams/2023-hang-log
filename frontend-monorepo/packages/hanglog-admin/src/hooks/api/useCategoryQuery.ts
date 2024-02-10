import { useSuspenseQuery } from '@tanstack/react-query';
import type { AxiosError } from 'axios';

import type { CategoryData } from '@type/category';

import { getCategory } from '@api/category/getCategory';

export const useCategoryQuery = () => {
  const { data: categoryData } = useSuspenseQuery<CategoryData[], AxiosError>({
    queryKey: ['category'],
    queryFn: getCategory,
    gcTime: 24 * 60 * 60 * 60 * 1000,
    staleTime: Infinity,
  });

  return { categoryData };
};
