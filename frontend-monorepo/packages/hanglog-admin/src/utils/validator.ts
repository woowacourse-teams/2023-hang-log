export const isEmptyString = (input: string) => {
  return /^$/.test(input);
};

export const isInvalidLatitude = (input: number) => {
  return input > 90 || input < -90;
};

export const isInvalidLongitude = (input: number) => {
  return input > 180 || input < -180;
};
