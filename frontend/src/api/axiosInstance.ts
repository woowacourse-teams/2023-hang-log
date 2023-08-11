import { BASE_URL, NETWORK } from '@constants/api';
import axios from 'axios';

import { checkAndSetToken, handleAPIError, handleTokenError } from '@api/interceptors';

export const axiosInstance = axios.create({
  baseURL: BASE_URL,
  timeout: NETWORK.TIMEOUT,
  withCredentials: true,
  useAuth: true,
});

axiosInstance.interceptors.request.use(checkAndSetToken, handleAPIError);

axiosInstance.interceptors.response.use((response) => response, handleTokenError);

axiosInstance.interceptors.response.use((response) => response, handleAPIError);
