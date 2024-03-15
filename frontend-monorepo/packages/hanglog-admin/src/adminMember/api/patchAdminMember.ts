import type { PasswordPatchData } from '@type/adminMember';

import { END_POINTS } from '@constants/api';

import { axiosInstance } from '../../common/api/axiosInstance';

export interface PatchPasswordParams extends PasswordPatchData {
  adminMemberId: number;
}

export const patchAdminMemberPassword = (
  { adminMemberId, ...passwordInformation }: PatchPasswordParams
) => {
  return axiosInstance.patch<PasswordPatchData>(
    END_POINTS.CHANGE_MEMBER_PASSWORD(adminMemberId),
    passwordInformation
  );
};
