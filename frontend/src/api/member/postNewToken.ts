import { END_POINTS } from '@constants/api';
import type { TokenData } from '@type/member';

import { axiosInstance } from '@api/axiosInstance';

export const postNewToken = async () => {
  const { data } = await axiosInstance.post<TokenData>(END_POINTS.TOKEN);

  return data;
};
