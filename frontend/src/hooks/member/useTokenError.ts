import { useNavigate } from 'react-router-dom';

import { useSetRecoilState } from 'recoil';

import { useToast } from '@hooks/common/useToast';

import { isLoggedInState } from '@store/auth';

import { ACCESS_TOKEN_KEY } from '@constants/api';
import { PATH } from '@constants/path';

export const useTokenError = () => {
  const navigate = useNavigate();

  const { generateToast } = useToast();

  const setIsLoggedIn = useSetRecoilState(isLoggedInState);

  const handleTokenError = () => {
    localStorage.removeItem(ACCESS_TOKEN_KEY);
    setIsLoggedIn(false);
    navigate(PATH.ROOT);

    generateToast('다시 로그인해 주세요.', 'error');
  };

  return { handleTokenError };
};
