import { useMutation, useQueryClient } from '@tanstack/react-query';

import { postTripItem } from '@api/tripItem/postTripItem';

export const useAddTripItemMutation = () => {
  const queryClient = useQueryClient();

  const addTripItemMutation = useMutation(postTripItem(), {
    onSuccess: () => {
      // 순서 변경 성공 시 Trip 정보 재요청
      queryClient.invalidateQueries({ queryKey: ['trip'] });
    },
    onError: () => {
      alert('오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
    },
  });

  return addTripItemMutation;
};
