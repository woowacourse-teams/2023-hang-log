import type { FormEvent } from 'react';
import { useCallback, useState } from 'react';

import type { AdminMemberFormData } from '@type/adminMember';

import { isEmptyString, isValidPassword } from '@utils/validator';

import { useAddAdminMemberMutation } from '../api/useAddAdminMemberMutation';

interface useAddAdminMemberFormParams {
  onSuccess?: () => void;
  onError?: () => void;
}

export const useAddAdminMemberForm = ({ onSuccess, onError }: useAddAdminMemberFormParams) => {
  const addAdminMemberMutaion = useAddAdminMemberMutation();

  const [adminMemberInformation, setAdminMemberInformation] = useState<AdminMemberFormData>({
    username: '',
    adminType: 'ADMIN',
    password: '',
    confirmPassword: '',
  });

  const [errors, setErrors] = useState({
    isUsernameError: false,
    isPasswordError: false,
    isConfirmPasswordError: false,
  });

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

  const disableError = useCallback((errorKey: keyof typeof errors) => {
    setErrors((prev) => ({ ...prev, [errorKey]: false }));
  }, []);

  const validateForm = () => {
    const { username, password, confirmPassword } = adminMemberInformation;
    const newErrors = {
      isUsernameError: isEmptyString(username.trim()),
      isPasswordError: !isValidPassword(password.trim()),
      isConfirmPasswordError: password !== confirmPassword,
    };

    setErrors(newErrors);

    return Object.values(newErrors).some((isError) => isError);
  };

  const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    if (validateForm()) {
      return;
    }

    addAdminMemberMutaion.mutate(
      {
        ...adminMemberInformation,
      },
      { onSuccess, onError }
    );
  };

  return {
    adminMemberInformation,
    errors,
    disableError,
    updateInputValue,
    handleSubmit,
  };
};
