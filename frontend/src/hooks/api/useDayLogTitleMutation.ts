import { useMutation } from '@tanstack/react-query';

import { patchDayLogTitle } from '@api/dayLog/patchDayLogTitle';

export const useDayLogTitleMutation = () => {
  const dayLogTitleMutation = useMutation(patchDayLogTitle());

  return dayLogTitleMutation;
};
