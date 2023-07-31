import { useMutation, useQueryClient } from '@tanstack/react-query';

import { deleteTrip } from '@api/trip/deleteTrip';

export const useDeleteTripMutation = () => {
  const queryClient = useQueryClient();

  const deleteTripMutation = useMutation(deleteTrip(), {
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['trips'] });
    },
  });

  return deleteTripMutation;
};
