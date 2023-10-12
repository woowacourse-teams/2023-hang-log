import { useQuery } from '@tanstack/react-query';

import { useRecoilValue } from 'recoil';

import type { AxiosError } from 'axios';

import { isLoggedInState } from '@store/auth';

import { getCommunityTrip } from '@api/trip/getCommunityTrip';

import type { TripData } from '@type/trip';

export const useCommunityTripQuery = (tripId: string) => {
  const isLoggedIn = useRecoilValue(isLoggedInState);

  const { data } = useQuery<TripData, AxiosError>(['PUBLISHED', tripId], () =>
    getCommunityTrip(tripId, isLoggedIn)
  );

  return { tripData: data! };
};
