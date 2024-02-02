import { useMutation } from '@tanstack/react-query';

import { useToast } from '../common/useToast';

import type { ErrorResponseData } from '@api/interceptors';

import { patchAdminMemberPassword } from '@/api/adminMember/patchAdminMember';

export const useUpdateAdminMemberPasswordMutation = () => {
  const { createToast } = useToast();

  const updateAdminMemberPasswordMutation = useMutation({
    mutationFn: patchAdminMemberPassword,
    onSuccess: () => {
      createToast('비밀번호를 성공적으로 수정했습니다.', 'success');
    },
    onError: (error: ErrorResponseData) => {
      createToast('비밀번호 수정에 실패했습니다. 잠시 후 다시 시도해 주세요.');
    },
  });

  return updateAdminMemberPasswordMutation;
};
