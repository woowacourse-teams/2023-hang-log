import { PasswordPatchData } from '@type/adminMember';

import { useForm } from '@hooks/useForm';

import { isEmptyString, isValidPassword } from '@utils/validator';

import { useUpdateAdminMemberPasswordMutation } from './useUpdateAdminMemberPasswordMutation';

interface useUpdatePasswordFormParams {
  adminMemberId: number;
  onSuccess?: () => void;
  onError?: () => void;
}

export interface PasswordFormData extends PasswordPatchData {
  confirmPassword: string;
}

export const useUpdatePasswordForm = (
  { adminMemberId, onSuccess, onError }: useUpdatePasswordFormParams
) => {
  const updatePasswordMutation = useUpdateAdminMemberPasswordMutation();

  const initialValues: PasswordFormData = {
    currentPassword: '',
    newPassword: '',
    confirmPassword: '',
  };

  const validate = (values: PasswordFormData) => {
    return {
      currentPassword: isEmptyString(values.currentPassword.trim()),
      newPassword: !isValidPassword(values.newPassword.trim()),
      confirmPassword: values.newPassword !== values.confirmPassword,
    };
  };

  const submitAction = (values: PasswordFormData, onSuccess?: () => void, onError?: () => void) => {
    updatePasswordMutation.mutate(
      {
        adminMemberId,
        currentPassword: values.currentPassword,
        newPassword: values.newPassword,
      },
      { onSuccess, onError }
    );
  };

  const { formValues, errors, updateInputValue, disableError, handleSubmit } = useForm(
    initialValues,
    validate,
    submitAction,
    { onSuccess, onError }
  );

  const adjustedErrors = {
    isCurrentPasswordError: errors.currentPassword,
    isNewPasswordError: errors.newPassword,
    isConfirmPasswordError: errors.confirmPassword,
  };

  return {
    passwordFormData: formValues,
    errors: adjustedErrors,
    disableError,
    updateInputValue,
    handleSubmit,
  };
};
