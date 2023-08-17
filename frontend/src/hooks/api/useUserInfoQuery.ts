import { useQuery } from '@tanstack/react-query';

import type { AxiosError } from 'axios';

import { getUserInfo } from '@api/member/getUserInfo';

import type { UserData } from '@type/member';

import { NETWORK } from '@constants/api';

export const useUserInfoQuery = () => {
  const { data } = useQuery<UserData, AxiosError>(['userInfo'], getUserInfo, {
    retry: NETWORK.RETRY_COUNT,
    suspense: true,
    useErrorBoundary: true,
  });

  return { userInfoData: data! };
};
