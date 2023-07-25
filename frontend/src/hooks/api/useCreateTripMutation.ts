import { NETWORK } from '@constants/api';
import { useMutation } from '@tanstack/react-query';

import { postNewTrip } from '@api/trips/postNewTrip';

export const useCreateTripMutation = () => {
  const newTripMutation = useMutation(postNewTrip(), {
    onError: () => {
      alert('오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
    },
    retry: NETWORK.RETRY_COUNT,
  });

  return newTripMutation;
};
