import type { CategoryData } from '@type/category';

import { useForm } from '@hooks/useForm';

import { isEmptyString, isEnglish, isInvalidCategoryId, isKorean } from '@utils/validator';

import { useAddCategoryMutation } from './useAddCategoryMutation';
import { useUpdateCategoryMutation } from './useUpdateCategoryMutation';

interface useAddCategoryFormParams {
  originalCategoryId?: number;
  initialData?: CategoryData;
  onSuccess?: () => void;
  onError?: () => void;
}

export const useAddCategoryForm = (
  { originalCategoryId, initialData, onSuccess, onError }: useAddCategoryFormParams
) => {
  const addCategoryMutation = useAddCategoryMutation();
  const updateCategoryMutation = useUpdateCategoryMutation();

  const initialValues: CategoryData = initialData ?? {
    id: 0,
    engName: '',
    korName: '',
  };

  const validate = (values: CategoryData) => {
    return {
      id: isInvalidCategoryId(values.id),
      engName: isEmptyString(values.engName.trim()) || !isEnglish(values.engName),
      korName: isEmptyString(values.korName.trim()) || !isKorean(values.korName),
    };
  };

  const submitAction = (values: CategoryData, onSuccess?: () => void, onError?: () => void) => {
    if (originalCategoryId !== undefined) {
      updateCategoryMutation.mutate({ ...values, id: originalCategoryId }, { onSuccess, onError });
    } else {
      addCategoryMutation.mutate(values, { onSuccess, onError });
    }
  };

  const { formValues, errors, updateInputValue, disableError, handleSubmit } = useForm(
    initialValues,
    validate,
    submitAction,
    { onSuccess, onError }
  );

  const adjustedErrors = {
    isIdError: errors.id,
    isEngNameError: errors.engName,
    isKorNameError: errors.korName,
  };

  return {
    categoryInformation: formValues,
    errors: adjustedErrors,
    disableError,
    updateInputValue,
    handleSubmit,
  };
};
