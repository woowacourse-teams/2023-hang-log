import { useMutation, useQueryClient } from '@tanstack/react-query';

import { useToast } from '@hooks/common/useToast';
import { useTokenError } from '@hooks/member/useTokenError';

import { patchDayLogTitle } from '@api/dayLog/patchDayLogTitle';
import type { ErrorResponseData } from '@api/interceptors';

import { ERROR_CODE } from '@constants/api';

export const useDayLogTitleMutation = () => {
  const queryClient = useQueryClient();

  const { createToast } = useToast();
  const { handleTokenError } = useTokenError();

  const dayLogTitleMutation = useMutation({
    mutationFn: patchDayLogTitle,
    onError: (error: ErrorResponseData) => {
      if (error.code && error.code > ERROR_CODE.TOKEN_ERROR_RANGE) {
        handleTokenError();

        return;
      }

      createToast('소제목 변경에 실패했습니다. 잠시 후 다시 시도해 주세요.', 'error');
    },
    onSuccess: (_, { tripId }) => {
      queryClient.invalidateQueries(['trip', tripId]);
    },
  });

  return dayLogTitleMutation;
};
