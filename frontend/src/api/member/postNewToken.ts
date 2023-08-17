import { axiosInstance } from '@api/axiosInstance';

import type { TokenData } from '@type/member';

import { END_POINTS } from '@constants/api';

export const postNewToken = async () => {
  const { data } = await axiosInstance.post<TokenData>(END_POINTS.TOKEN);

  return data;
};
