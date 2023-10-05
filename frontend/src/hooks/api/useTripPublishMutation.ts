import { useMutation, useQueryClient } from '@tanstack/react-query';

import { useToast } from '@hooks/common/useToast';

import { patchTripPublishStatus } from '@api/trip/patchTripPublishStatus';

import { TRIP_TYPE } from '@constants/trip';

export const useTripPublishStatusMutation = () => {
  const queryClient = useQueryClient();

  const { createToast } = useToast();

  const tripPublishStatusMutation = useMutation({
    mutationFn: patchTripPublishStatus,

    onSuccess: (_, { tripId }) => {
      queryClient.invalidateQueries({ queryKey: [TRIP_TYPE.PUBLISHED, tripId] });
    },

    onError: () => {
      createToast('커뮤니티 공개 설정에 실패했습니다. 잠시 후 다시 시도해주세요.');
    },
  });

  return tripPublishStatusMutation;
};
