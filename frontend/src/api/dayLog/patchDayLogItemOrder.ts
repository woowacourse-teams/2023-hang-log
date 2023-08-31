import { axiosInstance } from '@api/axiosInstance';

import { END_POINTS } from '@constants/api';

export interface PatchDayLogItemOrderParams {
  tripId: number;
  dayLogId: number;
  itemIds: number[];
}

export interface PatchDayLogOrderRequestBody {
  itemIds: number[];
}

export const patchDayLogItemOrder = ({ tripId, dayLogId, itemIds }: PatchDayLogItemOrderParams) => {
  return axiosInstance.patch<PatchDayLogOrderRequestBody>(
    END_POINTS.DAY_LOG_ORDER(tripId, dayLogId),
    {
      itemIds,
    }
  );
};
