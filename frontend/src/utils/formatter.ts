import { DateRangeData } from '@type/trips';

export const formatDate = (date: string) => {
  return date.replace(/-/g, '.');
};

export const formatMonthDate = (date: string) => {
  return formatDate(date).slice(5);
};

export const formatNumberToMoney = (number: number) => {
  return number === 0 ? 0 : number.toLocaleString();
};

export const dateRangeToString = (dateRange: DateRangeData) => {
  const { start, end } = dateRange;

  if (!start || !end) return '';

  return `${formatDate(start)} - ${formatDate(end)}`;
};

export const stringToOnlyLetter = (string: string) => {
  const koreanCharacterRegex = /^[A-Za-z가-힣ㄱ-ㅎ]+$/g;

  const matches = string.match(koreanCharacterRegex);

  if (matches) {
    return matches[0];
  }

  return '';
};
