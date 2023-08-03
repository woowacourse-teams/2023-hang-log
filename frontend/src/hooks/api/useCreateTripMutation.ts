import { useMutation } from '@tanstack/react-query';

import { postTrip } from '@api/trip/postTrip';

export const useCreateTripMutation = () => {
  const newTripMutation = useMutation(postTrip());

  return newTripMutation;
};
