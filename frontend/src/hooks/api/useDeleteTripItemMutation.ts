import { useMutation, useQueryClient } from '@tanstack/react-query';

import { useToast } from '@hooks/common/useToast';
import { useTokenError } from '@hooks/member/useTokenError';

import type { ErrorResponseData } from '@api/interceptors';
import { deleteTripItem } from '@api/tripItem/deleteTripItem';

import type { TripData } from '@type/trip';

import { ERROR_CODE } from '@constants/api';
import { TRIP_TYPE } from '@constants/trip';

export const useDeleteTripItemMutation = () => {
  const queryClient = useQueryClient();

  const { createToast } = useToast();
  const { handleTokenError } = useTokenError();

  const deleteTripItemMutation = useMutation({
    mutationFn: deleteTripItem,
    onMutate: ({ tripId, itemId }) => {
      const tripData = queryClient.getQueryData<TripData>([TRIP_TYPE.PERSONAL, 'trip', tripId]);

      queryClient.setQueryData<TripData>([TRIP_TYPE.PERSONAL, tripId], (prevTripData) => {
        if (!prevTripData) return prevTripData;

        const updatedDayLogs = prevTripData.dayLogs.map((dayLog) => {
          const updatedItems = dayLog.items.filter((item) => item.id !== itemId);

          return { ...dayLog, items: updatedItems };
        });

        return { ...prevTripData, dayLogs: updatedDayLogs };
      });

      return { tripData };
    },

    onError: (error: ErrorResponseData, { tripId }, context) => {
      if (error.code && error.code > ERROR_CODE.TOKEN_ERROR_RANGE) {
        handleTokenError();

        return;
      }

      queryClient.setQueryData<TripData>([TRIP_TYPE.PERSONAL, tripId], context?.tripData);

      createToast('아이템 삭제에 실패했습니다. 잠시 후 다시 시도해 주세요.');
    },

    onSettled: (data, error, { tripId }) => {
      queryClient.invalidateQueries({ queryKey: [TRIP_TYPE.PERSONAL, 'trip', tripId] });
    },
  });

  return deleteTripItemMutation;
};
