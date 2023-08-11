import { ACCESS_TOKEN_KEY } from '@constants/api';
import { PATH } from '@constants/path';
import { isLoggedInState } from '@store/auth';
import { toastListState } from '@store/toast';
import { useNavigate } from 'react-router-dom';
import { useSetRecoilState } from 'recoil';

import { generateUniqueId } from '@utils/uniqueId';

export const useTokenError = () => {
  const navigate = useNavigate();

  const setIsLoggedIn = useSetRecoilState(isLoggedInState);
  const setToastList = useSetRecoilState(toastListState);

  const handleTokenError = () => {
    localStorage.removeItem(ACCESS_TOKEN_KEY);
    setIsLoggedIn(false);
    navigate(PATH.ROOT);

    setToastList((prevToastList) => [
      ...prevToastList,
      {
        id: generateUniqueId(),
        variant: 'error',
        message: '다시 로그인해 주세요.',
      },
    ]);
  };

  return { handleTokenError };
};
