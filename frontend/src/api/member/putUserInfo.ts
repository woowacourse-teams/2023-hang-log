import { axiosInstance } from '@api/axiosInstance';

import type { UserData } from '@type/member';

import { END_POINTS } from '@constants/api';

export const putUserInfo = (data: UserData) => {
  return axiosInstance.put<UserData>(END_POINTS.MY_PAGE, { ...data });
};
