import { END_POINTS } from '@constants/api';
import type { TripSharedCodeData, TripSharedStatusData } from '@type/trip';

import { axiosInstance } from '@api/axiosInstance';

interface PatchTripSharedStatusParams extends TripSharedStatusData {
  tripId: number;
}

export const patchTripSharedStatus = async ({
  tripId,
  sharedStatus,
}: PatchTripSharedStatusParams) => {
  const { data } = await axiosInstance.patch<TripSharedCodeData>(END_POINTS.SHARE(tripId), {
    sharedStatus,
  });

  return data.sharedCode;
};
