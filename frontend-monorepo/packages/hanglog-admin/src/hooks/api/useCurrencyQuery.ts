import { useSuspenseQuery } from '@tanstack/react-query';
import type { AxiosError } from 'axios';

import type { CurrencyListData } from '@type/currency';

import { getCurrency } from '@api/currency/getCurrency';

export const useCurrencyQuery = (page: number, size: number) => {
  const { data: currencyListData } = useSuspenseQuery<CurrencyListData, AxiosError>({
    queryKey: ['currency', page, size],
    queryFn: () => getCurrency(page, size),
  });

  return { currencyListData };
};
