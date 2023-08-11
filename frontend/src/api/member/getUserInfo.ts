import { END_POINTS } from '@constants/api';
import type { UserData } from '@type/member';

import { axiosInstance } from '@api/axiosInstance';

export const getUserInfo = async () => {
  const { data } = await axiosInstance.get<UserData>(END_POINTS.MY_PAGE);

  return data;
};
