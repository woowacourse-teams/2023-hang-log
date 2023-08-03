import type { ToastType } from '@type/toast';
import { atom } from 'recoil';

export const toastListState = atom<ToastType[]>({
  key: 'toastList',
  default: [],
});
