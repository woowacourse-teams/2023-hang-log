import type { AdminMemberPostData } from '@type/adminMember';

import { END_POINTS } from '@constants/api';

import { axiosInstance } from '@api/axiosInstance';

export const postAdminMember = async (adminMemberFormData: AdminMemberPostData) => {
  const response = await axiosInstance.post(END_POINTS.MEMBER, adminMemberFormData);

  const adminMemberId = response.headers.location.replace(`${END_POINTS.MEMBER}/`, '');

  return adminMemberId;
};
