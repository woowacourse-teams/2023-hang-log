export const isEmptyString = (input: string) => {
  return /^$/.test(input);
};

export const isInvalidLatitude = (input: number) => {
  return input > 90 || input < -90;
};

export const isInvalidLongitude = (input: number) => {
  return input > 180 || input < -180;
};

export const isInvalidCategoryId = (input: number) => {
  return input > 999 || input < 100;
};

export const isEnglish = (input: string) => {
  return /^[A-Za-z]+$/.test(input);
};

export const isKorean = (input: string) => {
  return /^[가-힣]+$/.test(input);
};
