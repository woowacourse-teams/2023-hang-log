import { isLoggedInState } from '@/store/auth';

import { useQuery } from '@tanstack/react-query';

import { useRecoilValue } from 'recoil';

import type { AxiosError } from 'axios';

import { getCommunityTrip } from '@api/trip/getCommunityTrip';

import type { CommunityTripData } from '@type/trip';

export const useCommunityTripQuery = (tripId: string) => {
  const isLoggedIn = useRecoilValue(isLoggedInState);

  const { data } = useQuery<CommunityTripData, AxiosError>(['trip', tripId], () =>
    getCommunityTrip(tripId, isLoggedIn)
  );

  return { tripData: data! };
};
