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
      alert('오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
    },
  });

  return updateTripItemMutation;
};
