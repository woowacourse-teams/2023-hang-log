export interface AdminMemberData {
  id: number;
  username: string;
  adminType: string;
}

export interface AdminMemberPostData {
  username: string;
  adminType: string;
  password: string;
}

export interface AdminMemberFormData extends AdminMemberPostData {
  confirmPassword: string;
}

export type AdminTypeData = 'MASTER' | 'ADMIN';

export const SelectableAdminType = ['ADMIN'];

export interface PasswordPatchData {
  currentPassword: string;
  newPassword: string;
}

export interface PasswordFormData extends PasswordPatchData {
  confirmPassword: string;
}
