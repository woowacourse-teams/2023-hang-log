import { useMutation, useQueryClient } from '@tanstack/react-query';

import { useToast } from '../common/useToast';

import type { ErrorResponseData } from '@api/interceptors';

import { putCurrency } from '@/api/currency/putCurrency';

export const useUpdateCurrencyMutation = () => {
  const queryClient = useQueryClient();
  const { createToast } = useToast();

  const updateCurrencyMutation = useMutation({
    mutationFn: putCurrency,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['currency'] });
      createToast('환율 정보를 성공적으로 수정했습니다.', 'success');
    },
    onError: (error: ErrorResponseData) => {
      createToast('환율 정보 수정에 실패했습니다. 잠시 후 다시 시도해 주세요.');
    },
  });

  return updateCurrencyMutation;
};
