import { useMutation, useQueryClient } from '@tanstack/react-query';

import { postCurrency } from '@/currency/api/postCurrency';

import type { ErrorResponseData } from '@api/interceptors';

import { useToast } from '../../common/hooks/useToast';

export const useAddCurrencyMutation = () => {
  const queryClient = useQueryClient();
  const { createToast } = useToast();

  const addCurrencyMutation = useMutation({
    mutationFn: postCurrency,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['currency'] });
      createToast('환율 정보를 성공적으로 추가했습니다.', 'success');
    },
    onError: (error: ErrorResponseData) => {
      createToast('환율 정보 추가에 실패했습니다. 잠시 후 다시 시도해 주세요.');
    },
  });

  return addCurrencyMutation;
};
