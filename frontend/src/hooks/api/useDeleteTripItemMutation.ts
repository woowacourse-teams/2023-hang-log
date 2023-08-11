import { ERROR_CODE } from '@constants/api';
import { toastListState } from '@store/toast';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import type { TripData } from '@type/trip';
import { useSetRecoilState } from 'recoil';

import { generateUniqueId } from '@utils/uniqueId';

import type { ErrorResponseData } from '@api/interceptors';
import { deleteTripItem } from '@api/tripItem/deleteTripItem';

import { useTokenError } from '@hooks/member/useTokenError';

export const useDeleteTripItemMutation = () => {
  const queryClient = useQueryClient();

  const setToastList = useSetRecoilState(toastListState);

  const { handleTokenError } = useTokenError();

  const deleteTripItemMutation = useMutation({
    mutationFn: deleteTripItem,
    onMutate: ({ tripId, itemId }) => {
      const tripData = queryClient.getQueryData<TripData>(['trip', tripId]);

      queryClient.setQueryData<TripData>(['trip', tripId], (prevTripData) => {
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

      queryClient.setQueryData<TripData>(['trip', tripId], context?.tripData);

      setToastList((prevToastList) => [
        ...prevToastList,
        {
          id: generateUniqueId(),
          variant: 'error',
          message: '아이템 삭제에 실패했습니다. 잠시 후 다시 시도해 주세요.',
        },
      ]);
    },
    onSettled: (data, error, { tripId }) => {
      queryClient.invalidateQueries({ queryKey: ['trip', tripId] });
    },
  });

  return deleteTripItemMutation;
};
