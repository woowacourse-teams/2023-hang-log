import { NETWORK } from '@constants/api';
import { useMutation } from '@tanstack/react-query';
import { NewTripData } from '@type/trips';

import { postNewTrip } from '@api/trips/trips';

export const useNewTripMutation = () => {
  const newTripMutation = useMutation(
    async (newTripData: NewTripData) => postNewTrip(newTripData),
    {
      onError: (err, _, context) => {
        //toast 띄우기
        alert('오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
      },
      retry: NETWORK.RETRY_COUNT,
    }
  );

  return newTripMutation;
};
