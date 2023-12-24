import { match } from 'ts-pattern';

import { useSuspenseQuery } from '@tanstack/react-query';

import { useRecoilValue } from 'recoil';

import type { AxiosError } from 'axios';

import { isLoggedInState } from '@store/auth';

import { getCommunityTrip } from '@api/trip/getCommunityTrip';
import { getSharedTrip } from '@api/trip/getSharedTrip';
import { getTrip } from '@api/trip/getTrip';

import type { TripData, TripTypeData } from '@type/trip';

import { TRIP_TYPE } from '@constants/trip';

export const useTripQuery = (tripType: TripTypeData, tripId: string) => {
  const isLoggedIn = useRecoilValue(isLoggedInState);

  const { data: tripData } = useSuspenseQuery<TripData, AxiosError>({
    queryKey: [tripType, 'trip', tripId],
    queryFn: match(tripType)
      .with(TRIP_TYPE.PUBLISHED, () => () => getCommunityTrip(tripId, isLoggedIn))
      .with(TRIP_TYPE.SHARED, () => () => getSharedTrip(tripId))
      .otherwise(() => () => getTrip(tripId)),
    gcTime: 5 * 60 * 1000,
    staleTime: 60 * 1000,
  });

  return { tripData };
};
