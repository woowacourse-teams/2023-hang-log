import { useMutation } from '@tanstack/react-query';

import { useToast } from '@hooks/common/useToast';

import { patchTripPublishStatus } from '@api/trip/patchTripPublishStatus';

export const useTripPublishStatusMutation = () => {
  const { createToast } = useToast();

  const tripPublishStatusMutation = useMutation({
    mutationFn: patchTripPublishStatus,

    onError: () => {
      createToast('커뮤니티 공개 설정에 실패했습니다. 잠시 후 다시 시도해주세요.');
    },
  });

  return tripPublishStatusMutation;
};
