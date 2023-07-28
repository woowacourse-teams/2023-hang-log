import { useMutation, useQueryClient } from '@tanstack/react-query';

import { patchDayLogItemOrder } from '@api/dayLog/patchDayLogItemOrder';

export const useDayLogOrderMutation = () => {
  const queryClient = useQueryClient();

  const dayLogOrderMutation = useMutation(patchDayLogItemOrder(), {
    onSuccess: (_, { tripId }) => {
      queryClient.invalidateQueries({ queryKey: ['trip', tripId] });
    },
  });

  return dayLogOrderMutation;
};
