import { DateRangeData } from '@type/trips';

export const formatDate = (date: string) => {
  return date.replace(/-/g, '.');
};

export const formatMonthDate = (date: string) => {
  return formatDate(date).slice(5);
};

export const moneyFormatter = (money: number) => {
  return money === 0 ? 0 : money.toLocaleString();
};

export const dateRangeToString = (dateRange: DateRangeData) => {
  if (!dateRange.start || !dateRange.end) return '';

  return `${dateRange.start} ~ ${dateRange.end}`;
};
