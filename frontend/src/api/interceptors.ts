import { ACCESS_TOKEN_KEY, ERROR_CODE, HTTP_STATUS_CODE } from '@constants/api';
import { PATH } from '@constants/path';
import type { AxiosError, InternalAxiosRequestConfig } from 'axios';

import { HTTPError } from '@api/HTTPError';
import { axiosInstance } from '@api/axiosInstance';
import { postNewToken } from '@api/member/postNewToken';

export interface ErrorResponseData {
  statusCode?: number;
  message?: string;
  code?: number;
}

export const checkAndSetToken = (config: InternalAxiosRequestConfig) => {
  if (!config.headers || config.headers.Authorization || !config.useAuth) return config;

  const accessToken = localStorage.getItem(ACCESS_TOKEN_KEY);

  if (!accessToken) {
    window.location.href = PATH.ROOT;
    throw new Error('토큰이 유효하지 않습니다');
  }

  // eslint-disable-next-line no-param-reassign
  config.headers.Authorization = `Bearer ${accessToken}`;

  return config;
};

export const handleTokenError = async (error: AxiosError<ErrorResponseData>) => {
  const originalRequest = error.config;

  if (!error.response || !originalRequest) throw new Error('에러가 발생했습니다.');

  // ! 토큰 에러 처리!
  if (
    error.response.status === HTTP_STATUS_CODE.BAD_REQUEST &&
    error.response.data.code === ERROR_CODE.EXPIRED_ACCESS_TOKEN
  ) {
    const { accessToken } = await postNewToken();
    originalRequest.headers.Authorization = `Bearer ${accessToken}`;
    localStorage.setItem(ACCESS_TOKEN_KEY, accessToken);

    return axiosInstance(originalRequest);
  }

  if (
    error.response.status === HTTP_STATUS_CODE.BAD_REQUEST &&
    (error.response.data.code === ERROR_CODE.INVALID_ACCESS_TOKEN ||
      error.response.data.code === ERROR_CODE.INVALID_REFRESH_TOKEN ||
      error.response.data.code === ERROR_CODE.EXPIRED_REFRESH_TOKEN)
  ) {
    throw new HTTPError(
      error.response.status,
      error.response.data.message,
      error.response.data.code
    );
  }

  throw error;
};

export const handleAPIError = (error: AxiosError<ErrorResponseData>) => {
  if (!error.response) throw error;

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
