import { postTrip } from '@/api/trip/postTrip';
import { ERROR_MESSAGE, NETWORK } from '@constants/api';
import { useMutation } from '@tanstack/react-query';

export const useCreateTripMutation = () => {
  const newTripMutation = useMutation(postTrip(), {
    onError: (err, _, context) => {
      //TODO:toast 띄우기
      alert(ERROR_MESSAGE);
    },
    retry: NETWORK.RETRY_COUNT,
  });

  return newTripMutation;
};
