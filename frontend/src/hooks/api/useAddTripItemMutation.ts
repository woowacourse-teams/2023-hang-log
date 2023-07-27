import { useMutation, useQueryClient } from '@tanstack/react-query';

import { postTripItem } from '@api/tripItem/postTripItem';

export const useAddTripItemMutation = () => {
  const queryClient = useQueryClient();

  const addTripItemMutation = useMutation(postTripItem(), {
    onSuccess: (_, { tripId }) => {
      // ! 순서 변경 성공 시 Trip 정보 재요청
      queryClient.invalidateQueries({ queryKey: ['trip', tripId] });
    },
  });

  return addTripItemMutation;
};
