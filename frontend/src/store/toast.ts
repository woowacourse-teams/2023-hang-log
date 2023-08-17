import { atom } from 'recoil';

import type { ToastType } from '@type/toast';

export const toastListState = atom<ToastType[]>({
  key: 'toastList',
  default: [],
});
