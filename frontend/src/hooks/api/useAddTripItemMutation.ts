import { useMutation, useQueryClient } from '@tanstack/react-query';

import { useToast } from '@hooks/common/useToast';
import { useTokenError } from '@hooks/member/useTokenError';

import type { ErrorResponseData } from '@api/interceptors';
import { postTripItem } from '@api/tripItem/postTripItem';

import { ERROR_CODE } from '@constants/api';
import { TRIP_TYPE } from '@constants/trip';

export const useAddTripItemMutation = () => {
  const queryClient = useQueryClient();
  const { createToast } = useToast();

  const { handleTokenError } = useTokenError();

  const addTripItemMutation = useMutation({
    mutationFn: postTripItem,
    onSuccess: (_, { tripId }) => {
      queryClient.invalidateQueries({ queryKey: [TRIP_TYPE.PERSONAL, tripId] });
    },
    onError: (error: ErrorResponseData) => {
      if (error.code && error.code > ERROR_CODE.TOKEN_ERROR_RANGE) {
        handleTokenError();

        return;
      }

      createToast('아이템 추가에 실패했습니다. 잠시 후 다시 시도해 주세요.');
    },
  });

  return addTripItemMutation;
};
