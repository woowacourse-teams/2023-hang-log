import { atom } from 'recoil';

export const focusedIdState = atom<number>({
  key: 'focusedId',
  default: 0,
});

export const clickedMarkerState = atom<number>({
  key: 'clickedMarker',
  default: 0,
});
