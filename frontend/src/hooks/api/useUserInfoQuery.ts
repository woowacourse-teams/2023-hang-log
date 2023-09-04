import { useQuery } from '@tanstack/react-query';

import type { AxiosError } from 'axios';

import { getUserInfo } from '@api/member/getUserInfo';

import type { UserData } from '@type/member';

export const useUserInfoQuery = () => {
  const { data } = useQuery<UserData, AxiosError>(['userInfo'], getUserInfo);

  return { userInfoData: data! };
};
