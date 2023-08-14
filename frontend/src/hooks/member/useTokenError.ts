import { useNavigate } from 'react-router-dom';

import { useQueryClient } from '@tanstack/react-query';

import { useSetRecoilState } from 'recoil';

import { useToast } from '@hooks/common/useToast';

import { isLoggedInState } from '@store/auth';

import { ACCESS_TOKEN_KEY } from '@constants/api';
import { PATH } from '@constants/path';

export const useTokenError = () => {
  const navigate = useNavigate();

  const queryClient = useQueryClient();

  const { createToast } = useToast();

  const setIsLoggedIn = useSetRecoilState(isLoggedInState);

  const handleTokenError = () => {
    localStorage.removeItem(ACCESS_TOKEN_KEY);
    queryClient.clear();
    setIsLoggedIn(false);
    navigate(PATH.ROOT);

    createToast('다시 로그인해 주세요.');
  };

  return { handleTokenError };
};
