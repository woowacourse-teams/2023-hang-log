import type { FormEvent } from 'react';
import { useCallback, useState } from 'react';

import type { CategoryData } from '@type/category';

import { isEmptyString, isEnglish, isInvalidCategoryId, isKorean } from '@utils/validator';

import { useAddCategoryMutation } from '../api/useAddCategoryMutation';
import { useUpdateCategoryMutation } from '../api/useUpdateCategoryMutation';

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

  const [categoryInformation, setCategoryInformation] = useState<CategoryData>(
    initialData ?? {
      id: 0,
      engName: '',
      korName: '',
    }
  );

  const [errors, setErrors] = useState({
    isIdError: false,
    isEngNameError: false,
    isKorNameError: false,
  });

  const updateInputValue = useCallback(
    <K extends keyof CategoryData>(key: K, value: CategoryData[K]) => {
      setCategoryInformation((prevCategoryInformation) => {
        const data = {
          ...prevCategoryInformation,
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
    const { id, engName, korName } = categoryInformation;
    const newErrors = {
      isIdError: isInvalidCategoryId(id),
      isEngNameError: isEmptyString(engName.trim()) || !isEnglish(engName),
      isKorNameError: isEmptyString(korName.trim()) || !isKorean(korName),
    };

    setErrors(newErrors);

    return Object.values(newErrors).some((isError) => isError);
  };
  const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    if (validateForm()) {
      return;
    }

    if (!originalCategoryId) {
      addCategoryMutation.mutate(
        {
          ...categoryInformation,
        },
        { onSuccess, onError }
      );
      return;
    }

    updateCategoryMutation.mutate(
      {
        ...categoryInformation,
      },
      { onSuccess, onError }
    );
  };

  return {
    categoryInformation,
    errors,
    disableError,
    updateInputValue,
    handleSubmit,
  };
};
