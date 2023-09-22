import { useQuery } from '@tanstack/react-query';

import type { AxiosError } from 'axios';

import { getTrip } from '@api/trip/getTrip';

import type { TripData } from '@type/trip';

export const useTripQuery = (tripId: string) => {
  const { data } = useQuery<TripData, AxiosError>(['trip', tripId], () => getTrip(tripId));

  return { tripData: data! };
};
