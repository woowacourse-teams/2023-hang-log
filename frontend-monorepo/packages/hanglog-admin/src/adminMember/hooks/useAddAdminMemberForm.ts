import type { AdminMemberFormData } from '@type/adminMember';

import { useForm } from '@hooks/useForm';

import { isEmptyString, isValidPassword } from '@utils/validator';

import { useAddAdminMemberMutation } from './useAddAdminMemberMutation';

interface useAddAdminMemberFormParams {
  onSuccess?: () => void;
  onError?: () => void;
}

export const useAddAdminMemberForm = ({ onSuccess, onError }: useAddAdminMemberFormParams) => {
  const addAdminMemberMutation = useAddAdminMemberMutation();

  const initialValues: AdminMemberFormData = {
    username: '',
    adminType: 'ADMIN',
    password: '',
    confirmPassword: '',
  };

  const validate = (values: AdminMemberFormData) => {
    return {
      username: isEmptyString(values.username.trim()),
      password: !isValidPassword(values.password.trim()),
      confirmPassword: values.password !== values.confirmPassword,
    };
  };

  const submitAction = (
    values: AdminMemberFormData,
    onSuccess?: () => void,
    onError?: () => void
  ) => {
    addAdminMemberMutation.mutate(values, { onSuccess, onError });
  };

  const { formValues, errors, updateInputValue, disableError, handleSubmit } = useForm(
    initialValues,
    validate,
    submitAction,
    { onSuccess, onError }
  );

  const adjustedErrors = {
    isUsernameError: errors.username,
    isPasswordError: errors.password,
    isConfirmPasswordError: errors.confirmPassword,
  };

  return {
    adminMemberInformation: formValues,
    errors: adjustedErrors,
    disableError,
    updateInputValue,
    handleSubmit,
  };
};
