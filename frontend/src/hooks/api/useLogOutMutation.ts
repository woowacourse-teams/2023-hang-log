import { useNavigate } from 'react-router-dom';

import { useMutation, useQueryClient } from '@tanstack/react-query';

import { useSetRecoilState } from 'recoil';

import { isLoggedInState } from '@store/auth';
import { toastListState } from '@store/toast';

import { postLogout } from '@api/member/postLogOut';

import { generateUniqueId } from '@utils/uniqueId';

import { ACCESS_TOKEN_KEY } from '@constants/api';
import { PATH } from '@constants/path';

export const useLogOutMutation = () => {
  const navigate = useNavigate();

  const queryClient = useQueryClient();

  const setIsLoggedIn = useSetRecoilState(isLoggedInState);
  const setToastList = useSetRecoilState(toastListState);

  const logOutMutation = useMutation({
    mutationFn: postLogout,
    onSuccess: () => {
      localStorage.removeItem(ACCESS_TOKEN_KEY);
      queryClient.clear();
      setIsLoggedIn(false);
      navigate(PATH.ROOT);
    },
    onError: () => {
      setToastList((prevToastList) => [
        ...prevToastList,
        {
          id: generateUniqueId(),
          variant: 'error',
          message: '로그아웃에 실패했습니다. 잠시 후 다시 시도해 주세요.',
        },
      ]);
    },
  });

  return logOutMutation;
};
