import { useNavigate } from 'react-router-dom';

import { useMutation, useQueryClient } from '@tanstack/react-query';

import { useSetRecoilState } from 'recoil';

import { useToast } from '@hooks/common/useToast';

import { isLoggedInState } from '@store/auth';

import { deleteAccount } from '@api/member/deleteAccount';

import { ACCESS_TOKEN_KEY } from '@constants/api';
import { PATH } from '@constants/path';

export const useDeleteAccountMutation = () => {
  const navigate = useNavigate();
  const queryClient = useQueryClient();

  const setIsLoggedIn = useSetRecoilState(isLoggedInState);
  const { createToast } = useToast();

  const deleteAccountMutation = useMutation({
    mutationFn: deleteAccount,
    onSuccess: () => {
      localStorage.removeItem(ACCESS_TOKEN_KEY);
      queryClient.clear();
      setIsLoggedIn(false);
      navigate(PATH.ROOT);
    },
    onError: () => {
      createToast('회원 탈퇴에 실패했습니다. 잠시 후 다시 시도해 주세요.');
    },
  });

  return deleteAccountMutation;
};
