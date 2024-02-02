import { useSuspenseQuery } from '@tanstack/react-query';

import type { AxiosError } from 'axios';

import { getCurrency } from '@/api/currency/getCurrency';

import type { CurrencyListData } from '@/types/currency';

export const useCurrencyQuery = (page: number, size: number) => {
  const { data: currencyData } = useSuspenseQuery<CurrencyListData, AxiosError>({
    queryKey: ['currency', page, size],
    queryFn: () => getCurrency(page, size),
  });

  return { currencyData };
};
