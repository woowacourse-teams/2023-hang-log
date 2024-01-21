import { CALENDAR_MONTH_CHANGE } from '@constants/index';
import type { DateRangePickerCalendar, SelectedDateRange } from '@type/date';
import { useState } from 'react';

import { getNewYearMonthInfo, getYearMonthInfo, toDate } from '@utils/date';

export const useDateRangePicker = (initialSelectedDateRange?: SelectedDateRange) => {
  const currentDate = new Date();
  const initialDate = initialSelectedDateRange
    ? toDate(initialSelectedDateRange.startDate!)
    : currentDate;
  const currentMonthYearDetail = getYearMonthInfo(initialDate);

  const [calendarData, setCalendarData] = useState<DateRangePickerCalendar>({
    prevYearMonth: getNewYearMonthInfo(
      currentMonthYearDetail,
      CALENDAR_MONTH_CHANGE.PREVIOUS_MONTH
    ),
    currentYearMonth: currentMonthYearDetail,
  });

  const [selectedDateRange, setSelectedDateRange] = useState<SelectedDateRange>(
    initialSelectedDateRange ?? {
      startDate: null,
      endDate: null,
    }
  );

  const handleMonthChange = (change: number) => () => {
    setCalendarData((prevCalendarData) => {
      const newCalendarData = { ...prevCalendarData };

      if (change > 0) {
        newCalendarData.prevYearMonth = prevCalendarData.currentYearMonth;
        newCalendarData.currentYearMonth = getNewYearMonthInfo(
          newCalendarData.prevYearMonth,
          change
        );
      }

      if (change < 0) {
        newCalendarData.currentYearMonth = prevCalendarData.prevYearMonth;
        newCalendarData.prevYearMonth = getNewYearMonthInfo(
          newCalendarData.currentYearMonth,
          change
        );
      }

      return newCalendarData;
    });
  };

  const resetSelectedDateRange = () => {
    setSelectedDateRange({ startDate: null, endDate: null });
  };

  const handleDateSelect = (dateString: string, onDaySelect?: CallableFunction) => {
    setSelectedDateRange((prevSelectedDateRange) => {
      const startDate = prevSelectedDateRange.startDate
        ? new Date(prevSelectedDateRange.startDate)
        : null;
      const selectedDate = new Date(dateString);

      const nextSelectedDates: SelectedDateRange = {
        startDate: null,
        endDate: null,
      };

      if (startDate && !prevSelectedDateRange.endDate && selectedDate < startDate) {
        nextSelectedDates.startDate = dateString;
        nextSelectedDates.endDate = prevSelectedDateRange.startDate;
      } else if (startDate && !prevSelectedDateRange.endDate) {
        nextSelectedDates.startDate = prevSelectedDateRange.startDate;
        nextSelectedDates.endDate = dateString;
      } else {
        nextSelectedDates.startDate = dateString;
      }

      onDaySelect?.(nextSelectedDates);

      return nextSelectedDates;
    });
  };

  return {
    currentDate,
    calendarData,
    handleMonthChange,
    selectedDateRange,
    resetSelectedDateRange,
    handleDateSelect,
  };
};
