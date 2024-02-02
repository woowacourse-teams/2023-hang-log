import { useMutation, useQueryClient } from '@tanstack/react-query';

import { useToast } from '../common/useToast';

import type { ErrorResponseData } from '@api/interceptors';
import { postCategory } from '@/api/category/postCategory';

export const useAddCategoryMutation = () => {
  const queryClient = useQueryClient();
  const { createToast } = useToast();

  const addCategoryMutation = useMutation({
    mutationFn: postCategory,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['category'] });
      createToast('카테고리를 성공적으로 추가했습니다.', 'success');
    },
    onError: (error: ErrorResponseData) => {
      createToast('카테고리 추가에 실패했습니다. 잠시 후 다시 시도해 주세요.');
    },
  });

  return addCategoryMutation;
};
