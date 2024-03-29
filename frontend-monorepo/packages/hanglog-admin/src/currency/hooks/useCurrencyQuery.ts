import { useSuspenseQuery } from '@tanstack/react-query';
import type { AxiosError } from 'axios';

import { getCurrency } from '@/currency/api/getCurrency';

import type { CurrencyListData } from '@type/currency';

export const useCurrencyQuery = (page: number, size: number) => {
  const { data: currencyListData } = useSuspenseQuery<CurrencyListData, AxiosError>({
    queryKey: ['currency', page, size],
    queryFn: () => getCurrency(page, size),
  });

  return { currencyListData };
};
