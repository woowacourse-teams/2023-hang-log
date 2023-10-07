import { useQuery } from '@tanstack/react-query';

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

  const queryFn: { trip: () => Promise<TripData> } = {
    trip: () => getTrip(tripId),
  };

  if (tripType === TRIP_TYPE.PUBLISHED) {
    queryFn.trip = () => getCommunityTrip(tripId, isLoggedIn);
  }

  if (tripType === TRIP_TYPE.SHARED) {
    queryFn.trip = () => getSharedTrip(tripId);
  }

  const { data } = useQuery<TripData, AxiosError>([tripType, tripId], queryFn.trip);

  return { tripData: data! };
};
