import { TRIP_TYPE } from '@constants/trip';

import { useSuspenseQuery } from '@tanstack/react-query';

import type { AxiosError } from 'axios';

import { getSharedTrip } from '@api/trip/getSharedTrip';

import type { TripData } from '@type/trip';

export const useSharedTripQuery = (tripId: string) => {
  const { data: sharedTripData } = useSuspenseQuery<TripData, AxiosError>({
    queryKey: [TRIP_TYPE.SHARED, 'trip', tripId],
    queryFn: () => getSharedTrip(tripId),
  });

  return { sharedTripData };
};
