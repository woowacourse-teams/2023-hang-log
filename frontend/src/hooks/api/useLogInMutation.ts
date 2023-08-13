import { useNavigate } from 'react-router-dom';

import { useMutation } from '@tanstack/react-query';

import { useSetRecoilState } from 'recoil';

import { useToast } from '@hooks/common/useToast';

import { isLoggedInState } from '@store/auth';

import { axiosInstance } from '@api/axiosInstance';
import { postLogIn } from '@api/member/postLogIn';

import { ACCESS_TOKEN_KEY } from '@constants/api';
import { PATH } from '@constants/path';

export const useLogInMutation = () => {
  const navigate = useNavigate();
  const { createToast } = useToast();

  const setIsLoggedIn = useSetRecoilState(isLoggedInState);

  const logInMutation = useMutation({
    mutationFn: postLogIn,
    onSuccess: ({ accessToken }) => {
      localStorage.setItem(ACCESS_TOKEN_KEY, accessToken);
      axiosInstance.defaults.headers.Authorization = `Bearer ${accessToken}`;

      setIsLoggedIn(true);
    },
    onError: () => {
      setIsLoggedIn(false);

      createToast('오류가 발생했습니다. 잠시 후 다시 시도해주세요.', 'error');
    },
    onSettled: () => {
      navigate(PATH.ROOT);
    },
  });

  return { mutateLogIn: logInMutation.mutate };
};
