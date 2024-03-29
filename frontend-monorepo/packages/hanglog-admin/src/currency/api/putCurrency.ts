import type { CurrencyFormData } from '@type/currency';

import { END_POINTS } from '@constants/api';

import { axiosInstance } from '@api/axiosInstance';

export interface PutCurrencyParams extends CurrencyFormData {
  currencyId: number;
}

export const putCurrency = ({ currencyId, ...currencyInformation }: PutCurrencyParams) => {
  return axiosInstance.put<CurrencyFormData>(END_POINTS.CHANGE_CURRENCY(currencyId), {
    ...currencyInformation,
  });
};
