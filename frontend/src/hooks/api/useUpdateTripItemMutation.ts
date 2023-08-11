import { useMutation, useQueryClient } from '@tanstack/react-query';

import { useToast } from '@hooks/common/useToast';
import { useTokenError } from '@hooks/member/useTokenError';

import type { ErrorResponseData } from '@api/interceptors';
import { putTripItem } from '@api/tripItem/putTripItem';

import { ERROR_CODE } from '@constants/api';

export const useUpdateTripItemMutation = () => {
  const queryClient = useQueryClient();
  const { handleTokenError } = useTokenError();
  const { generateToast } = useToast();

  const updateTripItemMutation = useMutation({
    mutationFn: putTripItem,
    onSuccess: (_, { tripId }) => {
      queryClient.invalidateQueries({ queryKey: ['trip', tripId] });
    },
    onError: (error: ErrorResponseData) => {
      if (error.code && error.code > ERROR_CODE.TOKEN_ERROR_RANGE) {
        handleTokenError();

        return;
      }

      generateToast('아이템 수정에 실패했습니다. 잠시 후 다시 시도해 주세요.', 'error');
    },
  });

  return updateTripItemMutation;
};
