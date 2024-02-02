import type { FormEvent } from 'react';
import { useCallback, useState } from 'react';

import { useAddCurrencyMutation } from '../api/useAddCurrencyMutation';
import { useUpdateCurrencyMutation } from '../api/useUpdateCurrencyMutation';

import { isInvalidCurrency, isValidCurrencyDate } from '@/utils/validator';
import { currencyKeys } from '@/constants/currency';

import type { CurrencyFormData } from '@/types/currency';

interface UseAddCurrencyFormPrams {
  currencyId?: number;
  initialData?: CurrencyFormData;
  onSuccess?: () => void;
  onError?: () => void;
}

export const UseAddCurrencyForm = ({
  currencyId,
  initialData,
  onSuccess,
  onError,
}: UseAddCurrencyFormPrams) => {
  const addCurrencyMutation = useAddCurrencyMutation();
  const updateCurrencyMutation = useUpdateCurrencyMutation();

  const [currencyInformation, setCurrencyInformation] = useState<CurrencyFormData>(
    initialData ?? {
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
    }
  );

  const [isDateError, setIsDateError] = useState(false);

  const updateInputValue = useCallback(
    <K extends keyof CurrencyFormData>(key: string, value: CurrencyFormData[K]) => {
      setCurrencyInformation((prevCurrencyInformation) => {
        const data = {
          ...prevCurrencyInformation,
          [key]: value,
        };

        return data;
      });
    },
    []
  );

  const disableDateError = useCallback(() => {
    setIsDateError(false);
  }, []);

  //   const checkCurrencyValidity = (currencyInformation: CurrencyFormData) => {
  //     return currencyKeys.some((key) => isInvalidCurrency(Number(currencyInformation[key])));
  //   };

  const [currencyErrors, setCurrencyErrors] = useState<Record<string, boolean>>(
    currencyKeys.reduce((acc, key) => {
      acc[key] = false;
      return acc;
    }, {} as Record<string, boolean>)
  );
  const disableCurrencyError = useCallback((currencyKey: string) => {
    setCurrencyErrors((prevErrors) => ({
      ...prevErrors,
      [currencyKey]: false,
    }));
  }, []);

  const checkAndSetCurrencyValidity = useCallback(() => {
    const newCurrencyErrors = { ...currencyErrors };

    currencyKeys.forEach((key) => {
      newCurrencyErrors[key] = isInvalidCurrency(Number(currencyInformation[key]));
    });

    setCurrencyErrors(newCurrencyErrors);

    return Object.values(newCurrencyErrors).some((isError) => isError);
  }, [currencyInformation, currencyErrors]);

  const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    if (!isValidCurrencyDate(currencyInformation.date)) {
      setIsDateError(true);
      return;
    }

    // if (checkCurrencyValidity(currencyInformation)) {
    //   setIsCurrencyError(true);
    //   return;
    // }
    const isAnyCurrencyInvalid = checkAndSetCurrencyValidity();
    if (isAnyCurrencyInvalid) {
      return;
    }

    if (!currencyId) {
      addCurrencyMutation.mutate(
        {
          ...currencyInformation,
        },
        { onSuccess, onError }
      );

      return;
    }

    updateCurrencyMutation.mutate(
      {
        currencyId,
        ...currencyInformation,
      },
      { onSuccess, onError }
    );
  };

  return {
    currencyInformation,
    isDateError,
    currencyErrors,
    disableDateError,
    disableCurrencyError,
    updateInputValue,
    handleSubmit,
  };
};
