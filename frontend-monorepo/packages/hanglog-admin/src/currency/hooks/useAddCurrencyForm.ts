import type { CurrencyFormData } from '@type/currency';

import { useForm } from '@hooks/useForm';

import { currencyKeys } from '@constants/currency';

import { isInvalidCurrency, isValidCurrencyDate } from '@utils/validator';

import { useAddCurrencyMutation } from './useAddCurrencyMutation';
import { useUpdateCurrencyMutation } from './useUpdateCurrencyMutation';

interface useAddCurrencyFormParams {
  currencyId?: number;
  initialData?: CurrencyFormData;
  onSuccess?: () => void;
  onError?: () => void;
}

export const useAddCurrencyForm = (
  { currencyId, initialData, onSuccess, onError }: useAddCurrencyFormParams
) => {
  const addCurrencyMutation = useAddCurrencyMutation();
  const updateCurrencyMutation = useUpdateCurrencyMutation();

  const initialValues: CurrencyFormData = initialData ?? {
    date: '',
    usd: 0,
    eur: 0,
    gbp: 0,
    jpy: 0,
    cny: 0,
    chf: 0,
    sgd: 0,
    thb: 0,
    hkd: 0,
  };

  const validate = (values: CurrencyFormData) => {
    let errors: Record<string, boolean> = { date: !isValidCurrencyDate(values.date) };
    currencyKeys.forEach((key) => {
      errors[key] = isInvalidCurrency(Number(values[key]));
    });
    return errors;
  };

  const submitAction = (values: CurrencyFormData, onSuccess?: () => void, onError?: () => void) => {
    if (currencyId !== undefined) {
      updateCurrencyMutation.mutate({ ...values, currencyId }, { onSuccess, onError });
    } else {
      addCurrencyMutation.mutate(values, { onSuccess, onError });
    }
  };

  const { formValues, errors, updateInputValue, disableError, handleSubmit } = useForm(
    initialValues,
    validate,
    submitAction,
    { onSuccess, onError }
  );

  return {
    currencyInformation: formValues,
    errors,
    disableError: (currencyKey: string) => disableError(currencyKey),
    updateInputValue,
    handleSubmit,
  };
};
