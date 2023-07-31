import { getCity } from '@/api/city/getCity';
import { NETWORK } from '@constants/api';
import { useQuery } from '@tanstack/react-query';
import type { CityData } from '@type/city';
import { AxiosError } from 'axios';

export const useCityQuery = () => {
  const { data } = useQuery<CityData[], AxiosError>(['city'], getCity, {
    retry: NETWORK.RETRY_COUNT,
    // cacheTime: Infinity,
    suspense: true,
    useErrorBoundary: true,
  });

  return { cityData: data! };
};
