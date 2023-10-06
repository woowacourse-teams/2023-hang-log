import type { AxiosError, InternalAxiosRequestConfig } from 'axios';

import { HTTPError } from '@api/HTTPError';
import { axiosInstance } from '@api/axiosInstance';
import { postNewToken } from '@api/member/postNewToken';

import { ACCESS_TOKEN_KEY, ERROR_CODE, HTTP_STATUS_CODE } from '@constants/api';
import { PATH } from '@constants/path';

export interface ErrorResponseData {
  statusCode?: number;
  message?: string;
  code?: number;
}

export const checkAndSetToken = (config: InternalAxiosRequestConfig) => {
  if (!config.useAuth || !config.headers || config.headers.Authorization) return config;

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

  const { data, status } = error.response;

  if (status === HTTP_STATUS_CODE.BAD_REQUEST && data.code === ERROR_CODE.EXPIRED_ACCESS_TOKEN) {
    const { accessToken } = await postNewToken();
    originalRequest.headers.Authorization = `Bearer ${accessToken}`;
    localStorage.setItem(ACCESS_TOKEN_KEY, accessToken);

    return axiosInstance(originalRequest);
  }

  if (
    status === HTTP_STATUS_CODE.BAD_REQUEST &&
    (data.code === ERROR_CODE.INVALID_ACCESS_TOKEN ||
      data.code === ERROR_CODE.INVALID_REFRESH_TOKEN ||
      data.code === ERROR_CODE.EXPIRED_REFRESH_TOKEN ||
      data.code === ERROR_CODE.INVALID_TOKEN_VALIDATE ||
      data.code === ERROR_CODE.NULL_REFRESH_TOKEN ||
      data.code === ERROR_CODE.UNEXPECTED_TOKEN_ERROR ||
      data.code === ERROR_CODE.UNAUTHORIZED ||
      data.code === ERROR_CODE.INVALID_ACCESS_TOKEN)
  ) {
    localStorage.removeItem(ACCESS_TOKEN_KEY);

    throw new HTTPError(status, data.message, data.code);
  }

  throw error;
};

export const handleAPIError = (error: AxiosError<ErrorResponseData>) => {
  if (!error.response) throw error;

  const { data, status } = error.response;

  if (status >= HTTP_STATUS_CODE.INTERNAL_SERVER_ERROR) {
    throw new HTTPError(HTTP_STATUS_CODE.INTERNAL_SERVER_ERROR, data.message);
  }

  throw new HTTPError(status, data.message);
};
