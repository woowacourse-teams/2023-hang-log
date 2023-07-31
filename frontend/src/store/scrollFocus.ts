import { atom } from 'recoil';

export const focusedIdState = atom<number>({
  key: 'focusedId',
  default: 0,
});
