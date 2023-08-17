import { axiosInstance } from '@api/axiosInstance';

import { END_POINTS } from '@constants/api';

export const deleteAccount = () => {
  return axiosInstance.delete(END_POINTS.ACCOUNT);
};
