import { toastListState } from '@store/toast';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useSetRecoilState } from 'recoil';

import { generateUniqueId } from '@utils/uniqueId';

import { putTrip } from '@api/trip/putTrip';

export const useTripEditMutation = () => {
  const queryClient = useQueryClient();
  const setToastList = useSetRecoilState(toastListState);

  const tripMutation = useMutation({
    mutationFn: putTrip,
    onSuccess: (_, { tripId }) => {
      queryClient.invalidateQueries({ queryKey: ['trip', tripId] });
    },
    onError: () => {
      setToastList((prevToastList) => [
        ...prevToastList,
        {
          id: generateUniqueId(),
          variant: 'error',
          message: '여행 정보 변경에 실패했습니다. 잠시 후 다시 시도해주세요.',
        },
      ]);
    },
  });

  return tripMutation;
};
