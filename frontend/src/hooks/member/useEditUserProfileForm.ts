import type { FormEvent } from 'react';
import { useCallback, useState } from 'react';

import { useUserInfoMutation } from '@hooks/api/useUserInfoMutation';

import { isEmptyString } from '@utils/validator';

import type { UserData } from '@type/member';

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

  const isFormError = useCallback(() => {
    if (isEmptyString(userInfo.nickname)) {
      return true;
    }

    return false;
  }, [userInfo.nickname]);

  const disableNicknameError = useCallback(() => {
    setIsNicknameError(false);
  }, []);

  const handleSubmit = useCallback(
    (event: FormEvent<HTMLFormElement>) => {
      event.preventDefault();

      if (isFormError()) {
        setIsNicknameError(true);

        return;
      }

      userInfoMutation.mutate(userInfo);
    },
    [isFormError, userInfo, userInfoMutation]
  );

  return { userInfo, isNicknameError, updateInputValue, disableNicknameError, handleSubmit };
};
