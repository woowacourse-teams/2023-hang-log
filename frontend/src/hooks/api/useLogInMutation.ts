import { ACCESS_TOKEN_KEY } from '@constants/api';
import { PATH } from '@constants/path';
import { isLoggedInState } from '@store/auth';
import { toastListState } from '@store/toast';
import { useMutation } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { useSetRecoilState } from 'recoil';

import { generateUniqueId } from '@utils/uniqueId';

import { axiosInstance } from '@api/axiosInstance';
import { postLogIn } from '@api/member/postLogIn';

export const useLogInMutation = () => {
  const navigate = useNavigate();

  const setIsLoggedIn = useSetRecoilState(isLoggedInState);
  const setToastList = useSetRecoilState(toastListState);

  const logInMutation = useMutation({
    mutationFn: postLogIn,
    onSuccess: ({ accessToken }) => {
      localStorage.setItem(ACCESS_TOKEN_KEY, accessToken);
      axiosInstance.defaults.headers.Authorization = `Bearer ${accessToken}`;

      setIsLoggedIn(true);
    },
    onError: () => {
      setIsLoggedIn(false);

      setToastList((prevToastList) => [
        ...prevToastList,
        {
          id: generateUniqueId(),
          variant: 'error',
          message: '오류가 발생했습니다. 잠시 후 다시 시도해주세요.',
        },
      ]);
    },
    onSettled: () => {
      navigate(PATH.ROOT);
    },
  });

  return { mutateLogIn: logInMutation.mutate };
};
