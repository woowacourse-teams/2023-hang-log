import { useQuery } from '@tanstack/react-query';

import type { AxiosError } from 'axios';

import { getCity } from '@api/city/getCity';

import type { CityData } from '@type/city';

import { NETWORK } from '@constants/api';

export const useCityQuery = () => {
  const { data } = useQuery<CityData[], AxiosError>(['city'], getCity, {
    retry: NETWORK.RETRY_COUNT,
    suspense: true,
    useErrorBoundary: true,
  });

  return { cityData: data! };
};
