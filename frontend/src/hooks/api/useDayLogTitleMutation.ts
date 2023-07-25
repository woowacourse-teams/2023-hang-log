import { useMutation } from '@tanstack/react-query';

import { patchDayLogTitle } from '@api/dayLog/patchDayLogTitle';

export const useDayLogTitleMutation = () => {
  const dayLogTitleMutation = useMutation(patchDayLogTitle(), {
    onError: () => {
      alert('오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
    },
  });

  return dayLogTitleMutation;
};
