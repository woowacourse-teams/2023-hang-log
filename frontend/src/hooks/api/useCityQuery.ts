import { useQuery } from '@tanstack/react-query';

import type { AxiosError } from 'axios';

import { getCity } from '@api/city/getCity';

import type { CityData } from '@type/city';

export const useCityQuery = () => {
  const { data } = useQuery<CityData[], AxiosError>(['city'], getCity, {
    cacheTime: 24 * 60 * 60 * 60 * 1000,
    staleTime: Infinity,
  });

  return { cityData: data! };
};
