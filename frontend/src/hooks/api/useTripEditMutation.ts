import { useMutation, useQueryClient } from '@tanstack/react-query';

import { putTrip } from '@api/trip/putTrip';

export const useTripEditMutation = () => {
  const queryClient = useQueryClient();

  const tripMutation = useMutation(putTrip(), {
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['trip'] });
    },
  });

  return tripMutation;
};
