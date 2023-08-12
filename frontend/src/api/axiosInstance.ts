import axios from 'axios';

import { checkAndSetToken, handleAPIError, handleTokenError } from '@api/interceptors';

import { AXIOS_BASE_URL, NETWORK } from '@constants/api';

export const axiosInstance = axios.create({
  baseURL: AXIOS_BASE_URL,
  timeout: NETWORK.TIMEOUT,
  withCredentials: true,
  useAuth: true,
});

axiosInstance.interceptors.request.use(checkAndSetToken, handleAPIError);

axiosInstance.interceptors.response.use((response) => response, handleTokenError);

axiosInstance.interceptors.response.use((response) => response, handleAPIError);
