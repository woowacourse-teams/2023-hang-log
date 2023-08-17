import type { ChangeEvent } from 'react';
import { useState } from 'react';

import { useQueryClient } from '@tanstack/react-query';

import type { ExpenseCategoryData } from '@type/expense';
import type { TripItemFormData } from '@type/tripItem';

import { REGEX } from '@constants/regex';
import { DEFAULT_CURRENCY } from '@constants/trip';
import { AMOUNT_MAX_LIMIT } from '@constants/ui';

export const useTripItemExpenseInput = (
  updateInputValue: <K extends keyof TripItemFormData>(key: K, value: TripItemFormData[K]) => void,
  initialExpenseValue?: TripItemFormData['expense']
) => {
  const [, setExpenseValue] = useState<TripItemFormData['expense']>(
    initialExpenseValue ?? {
      currency: DEFAULT_CURRENCY,
      categoryId: 100,
      amount: 0,
    }
  );

  const queryClient = useQueryClient();
  const expenseCategoryData = queryClient.getQueryData<ExpenseCategoryData[]>(['expenseCategory'])!;

  const handleCategoryChange = (event: ChangeEvent<HTMLSelectElement>) => {
    setExpenseValue((prevExpenseValue) => {
      const newExpenseValue = { ...prevExpenseValue!, categoryId: Number(event.target.value) };

      if (prevExpenseValue?.amount) updateInputValue('expense', newExpenseValue);

      return newExpenseValue;
    });
  };

  const handleCurrencyChange = (event: ChangeEvent<HTMLSelectElement>) => {
    setExpenseValue((prevExpenseValue) => {
      const newExpenseValue = { ...prevExpenseValue!, currency: event.target.value };

      if (prevExpenseValue?.amount) updateInputValue('expense', newExpenseValue);

      return newExpenseValue;
    });
  };

  const handleAmountChange = (event: ChangeEvent<HTMLInputElement>) => {
    if (REGEX.ALPHABET_AND_KOREAN_CHARACTERS.test(event.target.value)) return;

    if (Number(event.target.value) < 0) {
      // eslint-disable-next-line no-param-reassign
      event.target.value = '0';
    }

    if (Number(event.target.value) > AMOUNT_MAX_LIMIT) {
      // eslint-disable-next-line no-param-reassign
      event.target.value = String(AMOUNT_MAX_LIMIT);
    }

    setExpenseValue((prevExpenseValue) => {
      const newExpenseValue = {
        ...prevExpenseValue!,
        amount: Number(event.target.value),
      };
      updateInputValue('expense', newExpenseValue.amount === 0 ? null : newExpenseValue);

      return newExpenseValue;
    });
  };

  return {
    expenseCategoryData,
    handleCategoryChange,
    handleCurrencyChange,
    handleAmountChange,
  };
};
