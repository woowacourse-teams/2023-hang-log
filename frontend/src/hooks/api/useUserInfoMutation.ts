import { useMutation, useQueryClient } from '@tanstack/react-query';

import { useToast } from '@hooks/common/useToast';
import { useTokenError } from '@hooks/member/useTokenError';

import type { ErrorResponseData } from '@api/interceptors';
import { putUserInfo } from '@api/member/putUserInfo';

import { ERROR_CODE } from '@constants/api';

export const useUserInfoMutation = () => {
  const queryClient = useQueryClient();

  const { generateToast } = useToast();
  const { handleTokenError } = useTokenError();

  const userInfoMutation = useMutation({
    mutationFn: putUserInfo,
    onSuccess: () => {
      queryClient.invalidateQueries(['userInfo']);

      generateToast('정보를 성공적으로 수정했습니다!', 'success');
    },
    onError: (error: ErrorResponseData) => {
      if (error.code && error.code > ERROR_CODE.TOKEN_ERROR_RANGE) {
        handleTokenError();

        return;
      }

      generateToast('정보 수정에 실패했습니다. 잠시 후 다시 시도해 주세요.', 'error');
    },
  });

  return userInfoMutation;
};
