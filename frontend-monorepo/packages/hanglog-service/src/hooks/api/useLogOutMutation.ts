import { useNavigate } from 'react-router-dom';

import { useMutation, useQueryClient } from '@tanstack/react-query';

import { useSetRecoilState } from 'recoil';

import { useToast } from '@hooks/common/useToast';

import { isLoggedInState } from '@store/auth';

import { deleteLogout } from '@api/member/deleteLogOut';

import { ACCESS_TOKEN_KEY } from '@constants/api';
import { PATH } from '@constants/path';

export const useLogOutMutation = () => {
  const navigate = useNavigate();
  const { createToast } = useToast();
  const queryClient = useQueryClient();

  const setIsLoggedIn = useSetRecoilState(isLoggedInState);

  const logOutMutation = useMutation({
    mutationFn: deleteLogout,
    onSuccess: () => {
      localStorage.removeItem(ACCESS_TOKEN_KEY);
      queryClient.clear();
      setIsLoggedIn(false);
      navigate(PATH.ROOT);
    },
    onError: () => {
      createToast('로그아웃에 실패했습니다. 잠시 후 다시 시도해 주세요.');
    },
  });

  return logOutMutation;
};
