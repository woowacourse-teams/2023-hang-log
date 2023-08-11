import { END_POINTS } from '@constants/api';
import type { TokenData } from '@type/member';

import { axiosInstance } from '@api/axiosInstance';

export const postLogout = (data: TokenData) => {
  return axiosInstance.post(END_POINTS.LOGOUT, data);
};
