import type { AxiosError } from 'axios';

import { HTTP_STATUS_CODE } from '@constants/api';

import { HTTPError } from '@api/HTTPError';

export interface ErrorResponseData {
  statusCode?: number;
  message?: string;
  code?: number;
}

export const handleAPIError = (error: AxiosError<ErrorResponseData>) => {
  if (!error.response) throw error;

  const { data, status } = error.response;

  if (status >= HTTP_STATUS_CODE.INTERNAL_SERVER_ERROR) {
    throw new HTTPError(HTTP_STATUS_CODE.INTERNAL_SERVER_ERROR, data.message);
  }

  throw new HTTPError(status, data.message, data.code);
};
