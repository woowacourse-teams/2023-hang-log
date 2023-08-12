import { axiosInstance } from '@api/axiosInstance';

import type { TokenData } from '@type/member';

import { END_POINTS } from '@constants/api';

export const postLogout = (data: TokenData) => {
  return axiosInstance.post(END_POINTS.LOGOUT, data);
};
