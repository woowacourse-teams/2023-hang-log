import { axiosInstance } from '@api/axiosInstance';

import { END_POINTS } from '@constants/api';

interface patchTripPublishStatusParams {
  tripId: string;
  publishedStatus: boolean;
}

export const patchTripPublishStatus = ({
  tripId,
  publishedStatus,
}: patchTripPublishStatusParams) => {
  return axiosInstance.patch(END_POINTS.PUBLISH(tripId), {
    publishedStatus,
  });
};
