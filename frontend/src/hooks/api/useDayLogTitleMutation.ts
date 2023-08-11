import { ERROR_CODE } from '@constants/api';
import { toastListState } from '@store/toast';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useSetRecoilState } from 'recoil';

import { generateUniqueId } from '@utils/uniqueId';

import { patchDayLogTitle } from '@api/dayLog/patchDayLogTitle';
import type { ErrorResponseData } from '@api/interceptors';

import { useTokenError } from '@hooks/member/useTokenError';

export const useDayLogTitleMutation = () => {
  const queryClient = useQueryClient();
  const setToastList = useSetRecoilState(toastListState);

  const { handleTokenError } = useTokenError();

  const dayLogTitleMutation = useMutation({
    mutationFn: patchDayLogTitle,
    onError: (error: ErrorResponseData) => {
      if (error.code && error.code > ERROR_CODE.TOKEN_ERROR_RANGE) {
        handleTokenError();

        return;
      }
      setToastList((prevToastList) => [
        ...prevToastList,
        {
          id: generateUniqueId(),
          variant: 'error',
          message: '소제목 변경에 실패했습니다. 잠시 후 다시 시도해 주세요.',
        },
      ]);
    },
    onSuccess: (_, { tripId }) => {
      queryClient.invalidateQueries(['trip', tripId]);
    },
  });

  return dayLogTitleMutation;
};
