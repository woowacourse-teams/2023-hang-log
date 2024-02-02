export interface AdminMemberData {
  id: number;
  username: string;
  adminType: string;
}

export interface AdminMemberFormData {
  username: string;
  adminType: AdminTypeData;
  password: string;
  confirmPassword: string;
}

export type AdminTypeData = 'MASTER' | 'ADMIN';

export interface PassowrdFormData {
  currentPassword: string;
  newPassword: string;
  confirmPassword: string;
}
