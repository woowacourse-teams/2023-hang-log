import { useSuspenseQuery } from '@tanstack/react-query';

import type { AxiosError } from 'axios';

import { getUserInfo } from '@api/member/getUserInfo';

import type { UserData } from '@type/member';

export const useUserInfoQuery = () => {
  const { data: userInfoData } = useSuspenseQuery<UserData, AxiosError>({
    queryKey: ['userInfo'],
    queryFn: getUserInfo,
    gcTime: 60 * 60 * 60 * 1000,
    staleTime: 60 * 60 * 60 * 1000,
  });

  return { userInfoData };
};
