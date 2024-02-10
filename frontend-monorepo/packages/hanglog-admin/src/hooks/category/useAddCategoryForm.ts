import type { FormEvent } from 'react';
import { useCallback, useState } from 'react';

import type { CategoryData } from '@type/category';

import { isEmptyString, isEnglish, isInvalidCategoryId, isKorean } from '@utils/validator';

import { useAddCategoryMutation } from '../api/useAddCategoryMutation';
import { useUpdateCategoryMutation } from '../api/useUpdateCategoryMutation';

interface UseAddCategoryFormParams {
  originalCategoryId?: number;
  initialData?: CategoryData;
  onSuccess?: () => void;
  onError?: () => void;
}

export const UseAddCategoryForm = (
  { originalCategoryId, initialData, onSuccess, onError }: UseAddCategoryFormParams
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

  const [isIdError, setIsIdError] = useState(false);
  const [isEngNameError, setIsEngNameError] = useState(false);
  const [isKorNameError, setIsKorNameError] = useState(false);

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

  const disableIdError = useCallback(() => {
    setIsIdError(false);
  }, []);

  const disableEngNameError = useCallback(() => {
    setIsEngNameError(false);
  }, []);

  const disableKorNameError = useCallback(() => {
    setIsKorNameError(false);
  }, []);

  const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    if (isInvalidCategoryId(categoryInformation.id)) {
      setIsIdError(true);
      return;
    }

    if (
      isEmptyString(categoryInformation.engName.trim()) ||
      !isEnglish(categoryInformation.engName)
    ) {
      setIsEngNameError(true);
      return;
    }

    if (
      isEmptyString(categoryInformation.korName.trim()) ||
      !isKorean(categoryInformation.korName)
    ) {
      setIsKorNameError(true);
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
    isIdError,
    isEngNameError,
    isKorNameError,
    disableIdError,
    disableEngNameError,
    disableKorNameError,
    updateInputValue,
    handleSubmit,
  };
};
