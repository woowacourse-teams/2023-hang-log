import { axiosInstance } from '@api/axiosInstance';

import type { AdminMemberPostData } from '@type/adminMember';

import { END_POINTS } from '@constants/api';

export const postAdminMember = async (adminMemberFormData: AdminMemberPostData) => {
  const response = await axiosInstance.post(END_POINTS.MEMBER, adminMemberFormData);

  const adminMemberId = response.headers.location.replace(`${END_POINTS.MEMBER}/`, '');

  return adminMemberId;
};
