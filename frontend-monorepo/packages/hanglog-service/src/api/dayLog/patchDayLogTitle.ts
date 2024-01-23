import { axiosInstance } from '@api/axiosInstance';

import { END_POINTS } from '@constants/api';

export interface PatchDayLogTitleRequestBody {
  title: string;
}

interface PatchDayLogTitleParams extends PatchDayLogTitleRequestBody {
  tripId: string;
  dayLogId: number;
}

export const patchDayLogTitle = ({ tripId, dayLogId, title }: PatchDayLogTitleParams) => {
  return axiosInstance.patch<PatchDayLogTitleRequestBody>(END_POINTS.DAY_LOG(tripId, dayLogId), {
    title,
  });
};
