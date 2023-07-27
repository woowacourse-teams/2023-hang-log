import { ERROR_MESSAGE, NETWORK } from '@constants/api';
import { useMutation, useQueryClient } from '@tanstack/react-query';

import { patchDayLogItemOrder } from '@api/dayLog/patchDayLogItemOrder';

export const useDayLogOrderMutation = () => {
  const queryClient = useQueryClient();

  const dayLogOrderMutation = useMutation(patchDayLogItemOrder(), {
    onSuccess: () => {
      // 순서 변경 성공 시 Trip 정보 재요청
      queryClient.invalidateQueries({ queryKey: ['trip'] });
    },
    onError: (err, _, context) => {
      // ? 에러 발생 했을 때 롤백하기??
      alert(ERROR_MESSAGE);
    },
    retry: NETWORK.RETRY_COUNT,
  });

  return dayLogOrderMutation;
};
