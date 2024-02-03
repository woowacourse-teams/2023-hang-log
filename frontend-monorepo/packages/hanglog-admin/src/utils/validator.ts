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

export const isValidCurrencyDate = (input: string) => {
  const regex = /^\d{4}-\d{2}-\d{2}$/;
  if (!regex.test(input)) return false;

  const [year, month, day] = input.split('-').map(Number);
  const dateObj = new Date(year, month - 1, day);

  return (
    dateObj.getFullYear() === year && dateObj.getMonth() === month - 1 && dateObj.getDate() === day
  );
};

export const isInvalidCurrency = (input: number) => {
  return input < 0 || input > 10000000;
};

export const isValidPassword = (input: string) => {
  return input.length >= 4;
};
