import type { ReactNode } from 'react';
import { useLayoutEffect } from 'react';

import { useSetRecoilState } from 'recoil';

import { isLoggedInState } from '@store/auth';

import { ACCESS_TOKEN_KEY } from '@constants/api';

interface LogInProps {
  children: ReactNode;
}

const LogIn = ({ children }: LogInProps) => {
  const setIsLoggedIn = useSetRecoilState(isLoggedInState);

  useLayoutEffect(() => {
    if (localStorage.getItem(ACCESS_TOKEN_KEY)) {
      setIsLoggedIn(true);
    }
  }, [setIsLoggedIn]);

  return children;
};

export default LogIn;
