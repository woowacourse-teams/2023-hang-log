import { useMutation, useQueryClient } from '@tanstack/react-query';

import { useToast } from '../common/useToast';

import type { ErrorResponseData } from '@api/interceptors';
import { putCategory } from '@api/category/putCategeory';

export const useUpdateCategoryMutation = () => {
  const queryClient = useQueryClient();
  const { createToast } = useToast();

  const updateCategoryMutation = useMutation({
    mutationFn: putCategory,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['category'] });
      createToast('카테고리를 성공적으로 수정했습니다.', 'success');
    },
    onError: (error: ErrorResponseData) => {
      createToast('카테고리 수정에 실패했습니다. 잠시 후 다시 시도해 주세요.');
    },
  });

  return updateCategoryMutation;
};
