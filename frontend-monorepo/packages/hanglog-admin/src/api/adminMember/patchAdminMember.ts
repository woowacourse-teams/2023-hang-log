import { axiosInstance } from '../axiosInstance';

import type { PassowrdPatchData } from '@/types/adminMember';

import { END_POINTS } from '@/constants/api';

export interface PatchPasswordParams extends PassowrdPatchData {
  adminMemberId: number;
}

export const patchAdminMemberPassword = ({
  adminMemberId,
  ...passwordInformation
}: PatchPasswordParams) => {
  return axiosInstance.patch<PassowrdPatchData>(
    END_POINTS.CHANGE_MEMBER_PASSWORD(adminMemberId),
    passwordInformation
  );
};
