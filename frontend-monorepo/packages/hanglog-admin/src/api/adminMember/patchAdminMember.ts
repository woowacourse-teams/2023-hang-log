import { axiosInstance } from '../axiosInstance';

import type { PassowrdFormData } from '@/types/adminMember';

import { END_POINTS } from '@/constants/api';

export interface PatchPasswordParams extends PassowrdFormData {
  adminMemberId: number;
}

export const patchAdminMemberPassword = ({
  adminMemberId,
  ...passwordInformation
}: PatchPasswordParams) => {
  return (
    axiosInstance.patch<PassowrdFormData>(END_POINTS.CHANGE_MEMBER_PASSWORD(adminMemberId)),
    { ...passwordInformation }
  );
};
