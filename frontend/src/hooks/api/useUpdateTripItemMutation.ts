import { ERROR_MESSAGE } from '@constants/api';
import { useMutation, useQueryClient } from '@tanstack/react-query';

import { putTripItem } from '@api/tripItem/putTripItem';

export const useUpdateTripItemMutation = () => {
  const queryClient = useQueryClient();

  const updateTripItemMutation = useMutation(putTripItem(), {
    onSuccess: (_, { tripId }) => {
      queryClient.invalidateQueries({ queryKey: ['trip', tripId] });
    },
  });

  return updateTripItemMutation;
};
