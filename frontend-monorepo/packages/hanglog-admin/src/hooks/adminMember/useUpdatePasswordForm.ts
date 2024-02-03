import type { FormEvent, ChangeEvent } from 'react';
import { useCallback, useState } from 'react';

import { useUpdateAdminMemberPasswordMutation } from '../api/useUpdateAdminMemberPasswordMutation';

import { isEmptyString, isValidPassword } from '@/utils/validator';

import type { PassowrdPatchData } from '@/types/adminMember';

interface UseUpdatePasswordFormParams {
  adminMemberId: number;
  onSuccess?: () => void;
  onError?: () => void;
}

export interface PassowrdFormData extends PassowrdPatchData {
  confirmPassword: string;
}

export const UseUpdatePasswordForm = ({
  adminMemberId,
  onSuccess,
  onError,
}: UseUpdatePasswordFormParams) => {
  const UpdatePasswordMutaion = useUpdateAdminMemberPasswordMutation();

  const [adminMemberInformation, setAdminMemberInformation] = useState<PassowrdFormData>({
    currentPassword: '',
    newPassword: '',
    confirmPassword: '',
  });

  const [isCurrentPasswordError, setIsCurrentPasswordError] = useState(false);
  const [isPasswordError, setIsPasswordError] = useState(false);
  const [isConfirmPasswordError, setIsConfirmPasswordError] = useState(false);

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

  const disableCurrentPasswordError = useCallback(() => {
    setIsCurrentPasswordError(false);
  }, []);

  const disablePasswordError = useCallback(() => {
    setIsPasswordError(false);
  }, []);

  const disableConfirmPasswordError = useCallback(() => {
    setIsConfirmPasswordError(false);
  }, []);

  const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    if (isEmptyString(adminMemberInformation.currentPassword.trim())) {
      setIsCurrentPasswordError(true);
      return;
    }

    if (!isValidPassword(adminMemberInformation.newPassword.trim())) {
      setIsPasswordError(true);
      return;
    }

    if (adminMemberInformation.newPassword != adminMemberInformation.confirmPassword) {
      setIsConfirmPasswordError(true);
      return;
    }

    UpdatePasswordMutaion.mutate(
      {
        adminMemberId: adminMemberId,
        currentPassword: adminMemberInformation.currentPassword,
        newPassword: adminMemberInformation.newPassword,
      },
      { onSuccess, onError }
    );
  };

  return {
    adminMemberInformation,
    isCurrentPasswordError,
    isPasswordError,
    isConfirmPasswordError,
    disableCurrentPasswordError,
    disablePasswordError,
    disableConfirmPasswordError,
    updateInputValue,
    handleSubmit,
  };
};

export default UseUpdatePasswordForm;
