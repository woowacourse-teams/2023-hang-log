import { axiosInstance } from '@api/axiosInstance';

import type { AdminMemberData } from '@type/adminMember';

import { END_POINTS } from '@constants/api';

export const getAdminMember = async () => {
  const { data } = await axiosInstance.get<AdminMemberData[]>(END_POINTS.MEMBER);
  return data;
};
