import { ERROR_MESSAGE } from '@constants/api';
import { useMutation, useQueryClient } from '@tanstack/react-query';

import { putTripItem } from '@api/tripItem/putTripItem';

export const useUpdateTripItemMutation = () => {
  const queryClient = useQueryClient();

  const updateTripItemMutation = useMutation(putTripItem(), {
    onSuccess: (_, { tripId }) => {
      // TODO : 낙관적 업데이트 적용?
      queryClient.invalidateQueries({ queryKey: ['trip', tripId] });
    },
    onError: () => {
      alert(ERROR_MESSAGE);
    },
  });

  return updateTripItemMutation;
};
