import { toastListState } from '@store/toast';
import { useMutation } from '@tanstack/react-query';
import { useSetRecoilState } from 'recoil';

import { generateUniqueId } from '@utils/uniqueId';

import { postTrip } from '@api/trip/postTrip';

export const useCreateTripMutation = () => {
  const setToastList = useSetRecoilState(toastListState);

  const newTripMutation = useMutation({
    mutationFn: postTrip,
    onError: () => {
      setToastList((prevToastList) => [
        ...prevToastList,
        {
          id: generateUniqueId(),
          variant: 'error',
          message: '새로운 여행기록을 생성하지 못했습니다. 잠시 후 다시 시도해주세요.',
        },
      ]);
    },
  });

  return newTripMutation;
};
