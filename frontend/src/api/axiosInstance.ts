import { BASE_URL } from '@constants/api';
import type { AxiosError } from 'axios';
import axios from 'axios';

import { HTTPError } from './HTTPError';

export const axiosInstance = axios.create({
  baseURL: BASE_URL,
  timeout: 10000,
});

interface ErrorResponseData {
  statusCode?: number;
  message?: string;
}

export const handleAPIError = (error: AxiosError<ErrorResponseData>) => {
  if (!error.response) throw Error('에러가 발생했습니다.');

  const { data, status } = error.response;

  throw new HTTPError(status, data.message);
};

axiosInstance.interceptors.response.use((response) => response, handleAPIError);
