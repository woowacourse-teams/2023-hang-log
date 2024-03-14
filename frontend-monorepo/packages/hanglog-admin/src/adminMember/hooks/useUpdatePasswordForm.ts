import type { FormEvent } from 'react';
import { useCallback, useState } from 'react';

import { PasswordPatchData } from '@type/adminMember';

import { isEmptyString, isValidPassword } from '@utils/validator';

import { useUpdateAdminMemberPasswordMutation } from './useUpdateAdminMemberPasswordMutation';

interface useUpdatePasswordFormParams {
  adminMemberId: number;
  onSuccess?: () => void;
  onError?: () => void;
}

export interface PassowrdFormData extends PasswordPatchData {
  confirmPassword: string;
}

export const useUpdatePasswordForm = (
  { adminMemberId, onSuccess, onError }: useUpdatePasswordFormParams
) => {
  const updatePasswordMutaion = useUpdateAdminMemberPasswordMutation();

  const [adminMemberInformation, setAdminMemberInformation] = useState<PassowrdFormData>({
    currentPassword: '',
    newPassword: '',
    confirmPassword: '',
  });

  const [errors, setErrors] = useState({
    isCurrentPasswordError: false,
    isPasswordError: false,
    isConfirmPasswordError: false,
  });

  const updateInputValue = useCallback(
    <K extends keyof PassowrdFormData>(key: K, value: PassowrdFormData[K]) => {
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
    const { currentPassword, newPassword, confirmPassword } = adminMemberInformation;
    const newErrors = {
      isCurrentPasswordError: isEmptyString(currentPassword.trim()),
      isPasswordError: !isValidPassword(newPassword.trim()),
      isConfirmPasswordError: newPassword !== confirmPassword,
    };

    setErrors(newErrors);

    return Object.values(newErrors).some((isError) => isError);
  };

  const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    if (validateForm()) {
      return;
    }

    updatePasswordMutaion.mutate(
      {
        adminMemberId,
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

export default useUpdatePasswordForm;
