import { useMutation, useQueryClient } from '@tanstack/react-query';

import { postAdminMember } from '@/adminMember/api/postAdminMember';

import type { ErrorResponseData } from '@api/interceptors';

import { useToast } from '../../common/hooks/useToast';

export const useAddAdminMemberMutation = () => {
  const queryClient = useQueryClient();
  const { createToast } = useToast();

  const addAdminMemberMutation = useMutation({
    mutationFn: postAdminMember,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['adminMember'] });
      createToast('관리자 멤버를 성공적으로 추가했습니다.', 'success');
    },
    onError: (error: ErrorResponseData) => {
      createToast('관리자 멤버 추가에 실패했습니다. 잠시 후 다시 시도해 주세요.');
    },
  });

  return addAdminMemberMutation;
};
