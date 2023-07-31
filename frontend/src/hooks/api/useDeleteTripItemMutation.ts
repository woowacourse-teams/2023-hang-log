import { useMutation, useQueryClient } from '@tanstack/react-query';

import { deleteTripItem } from '@api/tripItem/deleteTripItem';

export const useDeleteTripItemMutation = () => {
  const queryClient = useQueryClient();

  const deleteTripItemMutation = useMutation(deleteTripItem(), {
    onMutate: () => {
      // TODO : 낙관적 업데이트 적용 -> 에러 발생 시 토스트가 안 보이는 문제 때문에 일단 커멘트
      // const tripData = queryClient.getQueryData<TripData>(['trip', tripId]);
      // queryClient.setQueryData<TripData>(['trip', tripId], (prevTripData) => {
      //   if (!prevTripData) return prevTripData;
      //   const updatedDayLogs = prevTripData.dayLogs.map((dayLog) => {
      //     const updatedItems = dayLog.items.filter((item) => item.id !== itemId);
      //     return { ...dayLog, items: updatedItems };
      //   });
      //   return { ...prevTripData, dayLogs: updatedDayLogs };
      // });
      // return { tripData };
    },
    onSuccess: (_, { tripId }) => {
      queryClient.invalidateQueries({ queryKey: ['trip', tripId] });
    },
  });

  return deleteTripItemMutation;
};
