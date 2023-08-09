import type { UserData } from '@type/member';
import type { FormEvent } from 'react';
import { useCallback, useState } from 'react';

import { isEmptyString } from '@utils/validator';

import { useUserInfoMutation } from '@hooks/api/useUserInfoMutation';

export const useEditUserProfileForm = (initialData: UserData) => {
  const userInfoMutation = useUserInfoMutation();

  const [userInfo, setUserInfo] = useState(initialData);
  const [isNicknameError, setIsNicknameError] = useState(false);

  const updateInputValue = useCallback(<K extends keyof UserData>(key: K, value: UserData[K]) => {
    setUserInfo((prevUserInfo) => {
      const data = {
        ...prevUserInfo,
        [key]: value,
      };

      return data;
    });
  }, []);

  const isFormError = () => {
    if (isEmptyString(userInfo.nickname)) {
      return true;
    }

    return false;
  };

  const disableNicknameError = useCallback(() => {
    setIsNicknameError(false);
  }, []);

  const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    if (isFormError()) {
      setIsNicknameError(true);
    }

    userInfoMutation.mutate(userInfo);
  };

  return { userInfo, isNicknameError, updateInputValue, disableNicknameError, handleSubmit };
};
