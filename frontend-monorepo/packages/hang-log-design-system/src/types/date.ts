export interface YearMonth {
  /** 년도 문자열 */
  year: string;
  /** 월 문자열 - '06' 포맷 */
  month: string;
  /** 월의 시작일 Date */
  startDate: Date;
  /** 월의 첫 번째 요일 */
  firstDay: number;
  /** 월의 마지막 번째 날 */
  lastDate: number;
}

export interface DayInfo {
  /** 월의 Day 박스의 인덱스 */
  index: number;
  /** 현재 년월 정보 */
  yearMonthData: YearMonth;
  /** 현재 Date */
  currentDate: Date;
  /** 현재 선택된 날짜 범위 */
  dateRange?: SelectedDateRange;
  /** 최대로 선택할 수 있는 날짜 범위 */
  maxDateRange?: number;
  /** 오늘 이후 날짜를 막을 것인지에 대한 여부 */
  isFutureDaysRestricted?: boolean;
  /** 특정 범위를 벗어나는 날짜에 대해서 선택 불가능할지에 대한 여부 */
  hasRangeRestriction?: boolean;
  /** 현재 선택된 날짜 */
  selectedDate?: number;
}

export interface DateRangePickerCalendar {
  prevYearMonth: YearMonth;
  currentYearMonth: YearMonth;
}

export interface SelectedDateRange {
  startDate: string | null;
  endDate: string | null;
}
