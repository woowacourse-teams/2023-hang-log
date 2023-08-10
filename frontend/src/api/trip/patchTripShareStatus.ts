import { END_POINTS } from '@constants/api';
import type { TripSharedStatusData } from '@type/trip';

import { axiosInstance } from '@api/axiosInstance';

interface PatchTripSharedStatusParams extends TripSharedStatusData {
  tripId: number;
}

interface TripSharedUrlData {
  sharedUrl: string | null;
}

export const patchTripSharedStatus = async ({
  tripId,
  sharedStatus,
}: PatchTripSharedStatusParams) => {
  const { data } = await axiosInstance.patch<TripSharedUrlData>(END_POINTS.SHARE(tripId), {
    sharedStatus,
  });

  return data.sharedUrl;
};
