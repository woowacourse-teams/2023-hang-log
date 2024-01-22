import { CALENDAR_DATE_LENGTH } from '@constants/index';
import type { DayInfo, YearMonth } from '@type/date';

/** 2023-07-10 포맷인 날짜 문자열을 Date로 변경해 주는 함수 */
export const toDate = (dateString: string) => new Date(dateString);

/** Date를 2023-07-10 포맷인 날짜 문자열로 변경해 주는 함수 */
export const formatDate = (year: number | string, month: number | string, date: number | string) =>
  `${year}-${String(month).padStart(2, '0')}-${String(date).padStart(2, '0')}`;

/** 해당 월의 달력에 필요한 Day 박스의 개수를 리턴해 주는 함수 */
export const getDayBoxSize = (yearMonth: YearMonth) =>
  yearMonth.firstDay + yearMonth.lastDate <= CALENDAR_DATE_LENGTH.MIN
    ? CALENDAR_DATE_LENGTH.MIN
    : CALENDAR_DATE_LENGTH.MAX;

/** 특정 Date에 대해서 년월 정보를 가공해 주는 함수 */
export const getYearMonthInfo = (initialDate: Date) => {
  const month = String(initialDate.getMonth() + 1).padStart(2, '0');
  const year = String(initialDate.getFullYear());
  const startDate = new Date(`${year}-${month}`);
  const firstDay = startDate.getDay();
  const lastDate = new Date(initialDate.getFullYear(), initialDate.getMonth() + 1, 0).getDate();

  return { month, year, startDate, firstDay, lastDate };
};

/** 특정 년월의 이전/이후 년월 정보를 제공해 주는 함수 */
export const getNewYearMonthInfo = (currentDate: YearMonth, change: number = 1) => {
  const startDate = new Date(currentDate.startDate);
  startDate.setMonth(startDate.getMonth() + change);

  const newMonthYear = new Date(startDate);

  return getYearMonthInfo(newMonthYear);
};

/** 현재 날짜가 선택된 날짜 범위 안에 있는지에 대한 여부를 알려주는 함수 */
const isDayWithinRange = (
  dateString: string,
  startString: string | null,
  endString: string | null
) => {
  if (!startString || !endString) return false;

  const date = toDate(dateString);
  const start = toDate(startString);
  const end = toDate(endString);

  return start <= date && date <= end;
};

/** 현재 날짜가 오늘 이후 날짜인지에 대한 여부를 알려주는 함수 */
const isDayInFuture = (dateString: string, endDateString: string | null) => {
  if (!endDateString) return false;

  const date = toDate(dateString);
  const end = toDate(endDateString);

  return date > end;
};

/** 현재 날짜가 범위를 벗어났는지에 대한 여부를 알려주는 함수 */
const isDayOutOfRange = (
  dateRange: DayInfo['dateRange'],
  dateString: string,
  maxDateRange?: number
) => {
  if (!dateRange?.startDate || dateRange?.endDate || !maxDateRange) return false;

  const startDate = toDate(dateRange?.startDate); // Date 타입
  const currentDate = toDate(dateString); // Date 타입

  const diffMilliseconds = currentDate.getTime() - startDate.getTime();
  const diffDays = diffMilliseconds / (1000 * 60 * 60 * 24);

  return diffDays < -maxDateRange || diffDays > maxDateRange;
};

/** 특정 날에 대한 정보를 제공해 주는 함수 */
export const getDayInfo = ({
  index,
  yearMonthData,
  currentDate,
  dateRange,
  maxDateRange,
  isFutureDaysRestricted,
  hasRangeRestriction,
  selectedDate,
}: DayInfo) => {
  const date = index - yearMonthData.firstDay + 1;

  const dateString = formatDate(yearMonthData.year, yearMonthData.month, date);
  const todayDateString = formatDate(
    currentDate.getFullYear(),
    currentDate.getMonth() + 1,
    currentDate.getDate()
  );

  const isDate = index >= yearMonthData.firstDay && yearMonthData.lastDate >= date;
  const isToday = todayDateString === dateString;
  const isSelected =
    selectedDate === date ||
    dateRange?.startDate === dateString ||
    dateRange?.endDate === dateString;
  const isInRange =
    !!dateRange?.startDate &&
    !!dateRange?.endDate &&
    isDayWithinRange?.(dateString, dateRange.startDate, dateRange.endDate);
  const isRestricted =
    (isFutureDaysRestricted && isDayInFuture(dateString, todayDateString)) ||
    (hasRangeRestriction && isDayOutOfRange(dateRange, dateString, maxDateRange));

  return { date, isDate, dateString, isToday, isSelected, isInRange, isRestricted };
};
