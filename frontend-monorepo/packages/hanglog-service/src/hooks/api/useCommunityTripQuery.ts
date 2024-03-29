import { TRIP_TYPE } from '@constants/trip';

import { useSuspenseQuery } from '@tanstack/react-query';

import { useRecoilValue } from 'recoil';

import type { AxiosError } from 'axios';

import { isLoggedInState } from '@store/auth';

import { getCommunityTrip } from '@api/trip/getCommunityTrip';

import type { TripData } from '@type/trip';

export const useCommunityTripQuery = (tripId: string) => {
  const isLoggedIn = useRecoilValue(isLoggedInState);

  const { data: communityTripData } = useSuspenseQuery<TripData, AxiosError>({
    queryKey: [TRIP_TYPE.PUBLISHED, 'trip', tripId],
    queryFn: () => getCommunityTrip(tripId, isLoggedIn),
  });

  return { communityTripData };
};
