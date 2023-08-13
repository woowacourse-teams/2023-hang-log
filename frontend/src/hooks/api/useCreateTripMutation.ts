import { useMutation } from '@tanstack/react-query';

import { useToast } from '@hooks/common/useToast';
import { useTokenError } from '@hooks/member/useTokenError';

import type { ErrorResponseData } from '@api/interceptors';
import { postTrip } from '@api/trip/postTrip';

import { ERROR_CODE } from '@constants/api';

export const useCreateTripMutation = () => {
  const { createToast } = useToast();
  const { handleTokenError } = useTokenError();

  const newTripMutation = useMutation({
    mutationFn: postTrip,
    onError: (error: ErrorResponseData) => {
      if (error.code && error.code > ERROR_CODE.TOKEN_ERROR_RANGE) {
        handleTokenError();

        return;
      }

      createToast('새로운 여행기록을 생성하지 못했습니다. 잠시 후 다시 시도해주세요.');
    },
  });

  return newTripMutation;
};
