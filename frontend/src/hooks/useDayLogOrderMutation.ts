import { NETWORK } from '@constants/api';
import { useMutation, useQueryClient } from '@tanstack/react-query';

import { patchDayLogItemOrder } from '@api/dayLog/patchDayLogItemOrder';

export const useDayLogOrderMutation = () => {
  const queryClient = useQueryClient();

  const dayLogOrderMutation = useMutation(patchDayLogItemOrder(), {
    onSuccess: () => {
      /** 순서 변경 성공 시 Trip 정보 재요청 */
      queryClient.invalidateQueries({ queryKey: ['trip'] });
    },
    onError: (err, _, context) => {
      /** 에러 발생 했을 때 롤백하기?? */
      alert('오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
    },
    retry: NETWORK.RETRY_COUNT,
  });

  return dayLogOrderMutation;
};
