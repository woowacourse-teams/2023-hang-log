import type { AdminMemberData } from '@type/adminMember';

import { END_POINTS } from '@constants/api';

import { axiosInstance } from '@api/axiosInstance';

export const getAdminMember = async () => {
  const { data } = await axiosInstance.get<AdminMemberData[]>(END_POINTS.MEMBER);
  return data;
};
