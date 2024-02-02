import { axiosInstance } from '@api/axiosInstance';

import type { AdminMemberFormData } from '@type/adminMember';

import { END_POINTS } from '@constants/api';

export const postAdminmember = async (adminMemberFormData: AdminMemberFormData) => {
  const response = await axiosInstance.post(END_POINTS.MEMBER, adminMemberFormData);

  const adminMemberId = response.headers.location.replace(`${END_POINTS.MEMBER}/`, '');

  return adminMemberId;
};
