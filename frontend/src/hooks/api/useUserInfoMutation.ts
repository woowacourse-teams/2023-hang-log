import { useMutation, useQueryClient } from '@tanstack/react-query';

import { useSetRecoilState } from 'recoil';

import { useTokenError } from '@hooks/member/useTokenError';

import { toastListState } from '@store/toast';

import type { ErrorResponseData } from '@api/interceptors';
import { putUserInfo } from '@api/member/putUserInfo';

import { generateUniqueId } from '@utils/uniqueId';

import { ERROR_CODE } from '@constants/api';

export const useUserInfoMutation = () => {
  const queryClient = useQueryClient();

  const setToastList = useSetRecoilState(toastListState);

  const { handleTokenError } = useTokenError();

  const userInfoMutation = useMutation({
    mutationFn: putUserInfo,
    onSuccess: () => {
      setToastList((prevToastList) => [
        ...prevToastList,
        {
          id: generateUniqueId(),
          variant: 'success',
          message: '정보를 성공적으로 수정했습니다!',
        },
      ]);
      queryClient.invalidateQueries(['userInfo']);
    },
    onError: (error: ErrorResponseData) => {
      if (error.code && error.code > ERROR_CODE.TOKEN_ERROR_RANGE) {
        handleTokenError();

        return;
      }

      setToastList((prevToastList) => [
        ...prevToastList,
        {
          id: generateUniqueId(),
          variant: 'error',
          message: '정보 수정에 실패했습니다. 잠시 후 다시 시도해 주세요.',
        },
      ]);
    },
  });

  return userInfoMutation;
};
