import { axiosInstance } from '@api/axiosInstance';

import type { TripData, TripSharedStatusData } from '@type/trip';

import { END_POINTS } from '@constants/api';

interface PatchTripSharedStatusParams extends TripSharedStatusData {
  tripId: string;
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
