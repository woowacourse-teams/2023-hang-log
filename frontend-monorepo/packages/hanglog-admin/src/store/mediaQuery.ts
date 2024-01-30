import { atom } from 'recoil';

export const mediaQueryMobileState = atom({
  key: 'mediaQueryMobile',
  default: false,
});

export const viewportWidthState = atom({
  key: 'viewportWidth',
  default: 0,
});

export const viewportHeightState = atom({
  key: 'viewportHeight',
  default: 0,
});
