import { END_POINTS } from '@constants/api';

import { axiosInstance } from '@api/axiosInstance';

export interface PatchDayLogTitleRequestBody {
  title: string;
}

interface PatchDayLogTitleParams extends PatchDayLogTitleRequestBody {
  tripId: number;
  dayLogId: number;
}

export const patchDayLogTitle =
  () =>
  ({ tripId, dayLogId, title }: PatchDayLogTitleParams) => {
    return axiosInstance.patch<PatchDayLogTitleRequestBody>(END_POINTS.DAY_LOG(tripId, dayLogId), {
      title,
    });
  };
