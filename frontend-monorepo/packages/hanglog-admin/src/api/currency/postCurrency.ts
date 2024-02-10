import type { CurrencyFormData } from '@type/currency';

import { END_POINTS } from '@constants/api';

import { axiosInstance } from '@api/axiosInstance';

export const postCurrency = async (currencyFormData: CurrencyFormData) => {
  const response = await axiosInstance.post(END_POINTS.CURRENCY, currencyFormData);

  const currencyId = response.headers.location.replace(`${END_POINTS.CURRENCY}/`, '');

  return currencyId;
};
