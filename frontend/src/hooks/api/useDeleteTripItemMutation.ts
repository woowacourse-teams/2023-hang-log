import { useMutation, useQueryClient } from '@tanstack/react-query';

import { deleteTripItem } from '@api/tripItem/deleteTripItem';

export const useDeleteTripItemMutation = () => {
  const queryClient = useQueryClient();

  const deleteTripItemMutation = useMutation(deleteTripItem(), {
    onSuccess: (_, { tripId }) => {
      // TODO : 낙관적 업데이트 적용하기
      queryClient.invalidateQueries({ queryKey: ['trip', tripId] });
    },
    onError: () => {
      alert('오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
    },
  });

  return deleteTripItemMutation;
};
