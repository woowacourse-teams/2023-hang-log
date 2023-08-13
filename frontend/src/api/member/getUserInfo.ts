import { axiosInstance } from '@api/axiosInstance';

import type { UserData } from '@type/member';

import { END_POINTS } from '@constants/api';

export const getUserInfo = async () => {
  const { data } = await axiosInstance.get<UserData>(END_POINTS.MY_PAGE);

  return data;
};
