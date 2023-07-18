import { DateRange } from '@type/trip';

export const dateRangeToString = (dateRange: DateRange) => {
  if (!dateRange.start || !dateRange.end) return '';

  return `${dateRange.start} ~ ${dateRange.end}`;
};
