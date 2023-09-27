import { useQuery } from '@tanstack/react-query';

import type { AxiosError } from 'axios';

import { getCommunityTrip } from '@api/trip/getCommunityTrip';
import { getSharedTrip } from '@api/trip/getSharedTrip';
import { getTrip } from '@api/trip/getTrip';

import type { CommunityTripData, TripData } from '@type/trip';

type QueryFnType = (() => Promise<TripData>) | (() => Promise<CommunityTripData>);

export const useTripQuery = (
  tripId: string,
  isShared: boolean = false,
  isPublished: boolean = false
) => {
  const queryFn: { trip: QueryFnType } = {
    trip: () => getTrip(tripId),
  };

  if (isPublished) {
    queryFn.trip = () => getCommunityTrip(tripId);
  }

  if (isShared && !isPublished) {
    queryFn.trip = () => getSharedTrip(tripId);
  }

  const { data } = useQuery<TripData | CommunityTripData, AxiosError>(
    ['trip', tripId],
    queryFn.trip
  );

  return { tripData: data! };
};
