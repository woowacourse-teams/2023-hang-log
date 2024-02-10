import { useMutation, useQueryClient } from '@tanstack/react-query';

import { useToast } from '../common/useToast';

import type { ErrorResponseData } from '@api/interceptors';
import { putCity } from '@api/city/putCity';

export const useUpdateCityMutation = () => {
  const queryClient = useQueryClient();
  const { createToast } = useToast();

  const updateCityMutation = useMutation({
    mutationFn: putCity,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['city'] });
      createToast('도시를 성공적으로 수정했습니다.', 'success');
    },
    onError: (error: ErrorResponseData) => {
      createToast('도시 수정에 실패했습니다. 잠시 후 다시 시도해 주세요.');
    },
  });

  return updateCityMutation;
};
