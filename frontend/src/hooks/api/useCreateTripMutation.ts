import { ERROR_MESSAGE, NETWORK } from '@constants/api';
import { useMutation } from '@tanstack/react-query';

import { postNewTrip } from '@api/trips/postNewTrip';

export const useCreateTripMutation = () => {
  const newTripMutation = useMutation(postNewTrip(), {
    onError: (err, _, context) => {
      //TODO:toast 띄우기
      alert(ERROR_MESSAGE);
    },
    retry: NETWORK.RETRY_COUNT,
  });

  return newTripMutation;
};
