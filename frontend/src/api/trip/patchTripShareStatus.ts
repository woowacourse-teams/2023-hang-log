import { END_POINTS } from '@constants/api';
import type { TripData, TripSharedStatusData } from '@type/trip';

import { axiosInstance } from '@api/axiosInstance';

interface PatchTripSharedStatusParams extends TripSharedStatusData {
  tripId: number;
}

interface ResponseData {
  sharedCode: TripData['sharedCode'];
}

export const patchTripSharedStatus = async ({
  tripId,
  sharedStatus,
}: PatchTripSharedStatusParams) => {
  const { data } = await axiosInstance.patch<ResponseData>(END_POINTS.SHARE(tripId), {
    sharedStatus,
  });

  return data.sharedCode;
};
