import { BASE_URL, HTTP_STATUS_CODE } from '@constants/api';
import type { AxiosError } from 'axios';
import axios from 'axios';

import { HTTPError } from '@api/HTTPError';

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

  if (status >= HTTP_STATUS_CODE.INTERNAL_SERVER_ERROR) {
    throw new HTTPError(HTTP_STATUS_CODE.INTERNAL_SERVER_ERROR, data.message);
  }

  if (status === HTTP_STATUS_CODE.NOT_FOUND) {
    throw new HTTPError(HTTP_STATUS_CODE.NOT_FOUND, data.message);
  }

  if (status >= HTTP_STATUS_CODE.BAD_REQUEST) {
    throw new HTTPError(HTTP_STATUS_CODE.BAD_REQUEST, data.message);
  }
};

axiosInstance.interceptors.response.use((response) => response, handleAPIError);
