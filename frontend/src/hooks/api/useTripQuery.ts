import { getSharedTrip } from '@/api/trip/getSharedTrip';

import { useQuery } from '@tanstack/react-query';

import type { AxiosError } from 'axios';

import { getTrip } from '@api/trip/getTrip';

import type { TripData } from '@type/trip';

export const useTripQuery = (tripId: number | string, isShared: boolean = false) => {
  const { data } = useQuery<TripData, AxiosError>(['trip', tripId], () => getTrip(Number(tripId)), {
    enabled: !isShared,
  });

  const { data: sharedData } = useQuery<TripData, AxiosError>(
    ['trip', tripId],
    () => getSharedTrip(String(tripId)),
    {
      enabled: !!isShared,
    }
  );

  console.log(data);
  return { tripData: isShared ? sharedData! : data! };
};
