import { toastListState } from '@store/toast';
import { useMutation } from '@tanstack/react-query';
import { useSetRecoilState } from 'recoil';

import { generateUniqueId } from '@utils/uniqueId';

import { patchTripSharedStatus } from '@api/trip/patchTripShareStatus';

export const useTripShareStatusMutation = () => {
  const setToastList = useSetRecoilState(toastListState);

  const tripMutation = useMutation({
    mutationFn: patchTripSharedStatus,

    onError: () => {
      setToastList((prevToastList) => [
        ...prevToastList,
        {
          id: generateUniqueId(),
          variant: 'error',
          message: '여행 공유 설정 변경에 실패했습니다. 잠시 후 다시 시도해주세요.',
        },
      ]);
    },
  });

  return tripMutation;
};
