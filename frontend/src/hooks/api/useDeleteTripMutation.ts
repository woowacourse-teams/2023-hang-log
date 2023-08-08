import { toastListState } from '@store/toast';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useSetRecoilState } from 'recoil';

import { generateUniqueId } from '@utils/uniqueId';

import { deleteTrip } from '@api/trip/deleteTrip';

export const useDeleteTripMutation = () => {
  const queryClient = useQueryClient();
  const setToastList = useSetRecoilState(toastListState);

  const deleteTripMutation = useMutation({
    mutationFn: deleteTrip,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['trips'] });
    },
    onError: () => {
      setToastList((prevToastList) => [
        ...prevToastList,
        {
          id: generateUniqueId(),
          variant: 'error',
          message: '여행 삭제에 실패했습니다. 잠시 후 다시 시도해 주세요.',
        },
      ]);
    },
  });

  return deleteTripMutation;
};
