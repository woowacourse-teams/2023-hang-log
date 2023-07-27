import { ERROR_MESSAGE } from '@constants/api';
import { useMutation } from '@tanstack/react-query';

import { patchDayLogTitle } from '@api/dayLog/patchDayLogTitle';

export const useDayLogTitleMutation = () => {
  const dayLogTitleMutation = useMutation(patchDayLogTitle(), {
    onError: () => {
      alert(ERROR_MESSAGE);
    },
  });

  return dayLogTitleMutation;
};
