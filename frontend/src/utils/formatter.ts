import { REGEX } from '@constants/regex';
import type { DateRangeData } from '@type/trips';

export const formatDate = (date: string) => {
  return date.replace(/-/g, '.');
};

export const formatMonthDate = (date: string) => {
  return formatDate(date).slice(5);
};

export const formatNumberToMoney = (number: number) => {
  return number === 0 ? 0 : number.toLocaleString();
};

export const formatDateRange = ({ startDate, endDate }: DateRangeData) => {
  if (!startDate || !endDate) return '';

  return `${formatDate(startDate)} - ${formatDate(endDate)}`;
};

export const formatStringToLetter = (string: string) => {
  const letterRegex = new RegExp(REGEX.ONLY_LETTER, 'g');

  const matches = string.match(letterRegex);

  if (!matches) {
    return '';
  }

  return matches[0];
};
