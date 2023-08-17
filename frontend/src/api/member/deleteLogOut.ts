import { axiosInstance } from '@api/axiosInstance';

import { END_POINTS } from '@constants/api';

export const deleteLogout = () => {
  return axiosInstance.delete(END_POINTS.LOGOUT);
};
