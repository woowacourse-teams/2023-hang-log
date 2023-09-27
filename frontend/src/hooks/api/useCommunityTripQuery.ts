import { useQuery } from '@tanstack/react-query';

import type { AxiosError } from 'axios';

import { getCommunityTrip } from '@api/trip/getCommunityTrip';

import type { CommunityTripData } from '@type/trip';

export const useCommunityTripQuery = (tripId: string) => {
  const { data } = useQuery<CommunityTripData, AxiosError>(['trip', tripId], () =>
    getCommunityTrip(tripId)
  );

  return { tripData: data! };
};
