import { useMutation, useQueryClient } from '@tanstack/react-query';

import { patchDayLogTitle } from '@api/dayLog/patchDayLogTitle';

export const useDayLogTitleMutation = () => {
  const queryClient = useQueryClient();

  const dayLogTitleMutation = useMutation(patchDayLogTitle(), {
    onSuccess: (_, { tripId }) => {
      queryClient.invalidateQueries(['trip', tripId]);
    },
  });

  return dayLogTitleMutation;
};
