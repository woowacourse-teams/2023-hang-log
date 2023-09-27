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
  let queryFn: QueryFnType = () => getTrip(tripId);

  if (isPublished) {
    queryFn = () => getCommunityTrip(tripId);
  }

  if (isShared && !isPublished) {
    queryFn = () => getSharedTrip(tripId);
  }

  const { data } = useQuery<TripData | CommunityTripData, AxiosError>(['trip', tripId], queryFn);

  return { tripData: data! };
};
