import { ERROR_MESSAGE, NETWORK } from '@constants/api';
import { useMutation } from '@tanstack/react-query';

import { postTrip } from '@api/trip/postTrip';

export const useCreateTripMutation = () => {
  const newTripMutation = useMutation(postTrip(), {
    onError: () => {
      // TODO:toast 띄우기
      // eslint-disable-next-line no-alert
      alert(ERROR_MESSAGE);
    },
    retry: NETWORK.RETRY_COUNT,
  });

  return newTripMutation;
};
