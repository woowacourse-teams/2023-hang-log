import { END_POINTS } from '@constants/api';
import type { UserData } from '@type/member';

import { axiosInstance } from '@api/axiosInstance';

export const putUserInfo = (data: UserData) => {
  return axiosInstance.put<UserData>(END_POINTS.MY_PAGE, { ...data });
};
