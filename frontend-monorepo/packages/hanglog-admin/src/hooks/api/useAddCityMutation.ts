import { useMutation, useQueryClient } from '@tanstack/react-query';

import { postCity } from '@api/city/postCity';
import type { ErrorResponseData } from '@api/interceptors';

import { useToast } from '../common/useToast';

export const useAddCityMutation = () => {
  const queryClient = useQueryClient();
  const { createToast } = useToast();

  const addCityMutation = useMutation({
    mutationFn: postCity,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['city'] });
      createToast('도시를 성공적으로 추가했습니다.', 'success');
    },
    onError: (error: ErrorResponseData) => {
      createToast('도시 추가에 실패했습니다. 잠시 후 다시 시도해 주세요.');
    },
  });

  return addCityMutation;
};
