import type { AxiosResponse } from 'axios';

import { axiosInstance } from '@api/axiosInstance';

import type { TokenData } from '@type/member';

import { END_POINTS } from '@constants/api';

interface PostLogInRequestBody {
  code: string;
}

interface PostLogInParams extends PostLogInRequestBody {
  provider: string;
}

export const postLogIn = async ({ provider, code }: PostLogInParams) => {
  const { data } = await axiosInstance.post<PostLogInRequestBody, AxiosResponse<TokenData>>(
    END_POINTS.LOGIN(provider),
    { code },
    { useAuth: false }
  );

  return data;
};
