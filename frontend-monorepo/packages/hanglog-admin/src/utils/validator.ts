import { REGEX } from '@constants/regex';
import {
  ADMIN_MEMBER_MIN_PASSWORD_LENGTH,
  CATEGORY_ID_MAX,
  CATEGORY_ID_MIN,
  CITY_LATITUDE_MAX,
  CITY_LATITUDE_MIN,
  CITY_LONGITUDE_MAX,
  CITY_LONGITUDE_MIN,
  CURRENCY_MAX,
  CURRENCY_MIN,
} from '@constants/ui';

export const isEmptyString = (input: string) => {
  return /^$/.test(input);
};

export const isInvalidLatitude = (input: number) => {
  return input > CITY_LATITUDE_MAX || input < CITY_LATITUDE_MIN;
};

export const isInvalidLongitude = (input: number) => {
  return input > CITY_LONGITUDE_MAX || input < CITY_LONGITUDE_MIN;
};

export const isInvalidCategoryId = (input: number) => {
  return input > CATEGORY_ID_MAX || input < CATEGORY_ID_MIN;
};

export const isEnglish = (input: string) => {
  return REGEX.ALPHABET.test(input);
};

export const isKorean = (input: string) => {
  return REGEX.KOREAN_CHARACTERS.test(input);
};

export const isValidCurrencyDate = (input: string) => {
  if (!REGEX.DATE.test(input)) return false;

  const [year, month, day] = input.split('-').map(Number);
  const dateObj = new Date(year, month - 1, day);

  return (
    dateObj.getFullYear() === year && dateObj.getMonth() === month - 1 && dateObj.getDate() === day
  );
};

export const isInvalidCurrency = (input: number) => {
  return input < CURRENCY_MIN || input > CURRENCY_MAX;
};

export const isValidPassword = (input: string) => {
  return input.length >= ADMIN_MEMBER_MIN_PASSWORD_LENGTH;
};
