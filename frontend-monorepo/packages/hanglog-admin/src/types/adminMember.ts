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

export interface AdminMemberFormData {
  username: string;
  adminType: string;
  password: string;
  confirmPassword: string;
}

export type AdminTypeData = 'MASTER' | 'ADMIN';

export const SelectableAdminType = ['ADMIN'];

export interface PassowrdFormData {
  currentPassword: string;
  newPassword: string;
  confirmPassword: string;
}
