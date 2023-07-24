import { useMutation, useQueryClient } from '@tanstack/react-query';

import { putTrip } from '@api/trip/putTrip';

export const useEditTripMutation = () => {
  const queryClient = useQueryClient();

  const tripMutation = useMutation(putTrip(), {
    onSuccess: () => {
      // 여행 정보 수정 성공시 Trip 정보 재요청
      queryClient.invalidateQueries({ queryKey: ['trip'] });
    },
    onError: (err, _, context) => {
      alert('오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
    },
  });

  return tripMutation;
};
