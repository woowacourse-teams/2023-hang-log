import type { FormEvent, ChangeEvent } from 'react';
import { useCallback, useState } from 'react';

import { useAddAdminMemberMutation } from '../api/useAddAdminMemberMutation';

import { isEmptyString, isValidPassword } from '@/utils/validator';

import type { AdminMemberFormData } from '@/types/adminMember';

interface UseAddAdminMemberFormParams {
  onSuccess?: () => void;
  onError?: () => void;
}

export const UseAddAdminMemberForm = ({ onSuccess, onError }: UseAddAdminMemberFormParams) => {
  const addAdminMemberMutaion = useAddAdminMemberMutation();

  const [adminMemberInformation, setAdminMemberInformation] = useState<AdminMemberFormData>({
    username: '',
    adminType: 'ADMIN',
    password: '',
    confirmPassword: '',
  });

  const [isUsernameError, setIsUsernameError] = useState(false);
  const [isPasswordError, setIsPasswordError] = useState(false);
  const [isConfirmPasswordError, setIsConfirmPasswordError] = useState(false);

  const updateInputValue = useCallback(
    <K extends keyof AdminMemberFormData>(key: K, value: AdminMemberFormData[K]) => {
      setAdminMemberInformation((prevAdminMemberInformation) => {
        const data = {
          ...prevAdminMemberInformation,
          [key]: value,
        };

        return data;
      });
    },
    []
  );

  const disableUsernameError = useCallback(() => {
    setIsUsernameError(false);
  }, []);

  const disablePasswordError = useCallback(() => {
    setIsPasswordError(false);
  }, []);

  const disableConfirmPasswordError = useCallback(() => {
    setIsConfirmPasswordError(false);
  }, []);

  const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    if (isEmptyString(adminMemberInformation.username.trim())) {
      setIsUsernameError(true);
      return;
    }

    if (!isValidPassword(adminMemberInformation.password.trim())) {
      setIsPasswordError(true);
      return;
    }

    if (adminMemberInformation.password != adminMemberInformation.confirmPassword) {
      setIsConfirmPasswordError(true);
      return;
    }

    addAdminMemberMutaion.mutate(
      {
        username: adminMemberInformation.username,
        adminType: adminMemberInformation.adminType,
        password: adminMemberInformation.password,
      },
      { onSuccess, onError }
    );
  };

  return {
    adminMemberInformation,
    isUsernameError,
    isPasswordError,
    isConfirmPasswordError,
    disableUsernameError,
    disablePasswordError,
    disableConfirmPasswordError,
    updateInputValue,
    handleSubmit,
  };
};
