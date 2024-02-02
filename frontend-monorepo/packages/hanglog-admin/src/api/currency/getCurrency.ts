import { axiosInstance } from '@api/axiosInstance';

import type { CurrencyListData } from '@type/currency';

import { END_POINTS } from '@constants/api';

export const getCurrency = async (page: number, size: number) => {
  const { data } = await axiosInstance.get<CurrencyListData>(END_POINTS.CURRENCY_PAGE(page, size));
  return data;
};
