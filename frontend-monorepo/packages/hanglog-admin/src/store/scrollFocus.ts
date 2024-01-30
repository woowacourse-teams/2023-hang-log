import { atom } from 'recoil';

export const focusedIdState = atom<number>({
  key: 'focusedId',
  default: 0,
});

export const clickedMarkerIdState = atom<number>({
  key: 'clickedMarkerId',
  default: 0,
});
