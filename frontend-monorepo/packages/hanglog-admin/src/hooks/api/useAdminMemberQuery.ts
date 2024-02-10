import { useSuspenseQuery } from '@tanstack/react-query';

import type { AxiosError } from 'axios';

import { getAdminMember } from '@api/adminMember/getAdminMember';

import type { AdminMemberData } from '@type/adminMember';

export const useAdminMemberQuery = () => {
  const { data: adminMemberData } = useSuspenseQuery<AdminMemberData[], AxiosError>({
    queryKey: ['adminMember'],
    queryFn: getAdminMember,
    gcTime: 24 * 60 * 60 * 60 * 1000,
    staleTime: Infinity,
  });

  return { adminMemberData };
};
