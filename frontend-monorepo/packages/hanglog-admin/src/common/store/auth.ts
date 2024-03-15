import { atom } from 'recoil';

export const isLoggedInState = atom({
  key: 'isLoggedIn',
  default: false,
});
