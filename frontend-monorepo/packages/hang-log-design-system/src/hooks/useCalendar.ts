import type { YearMonth } from '@type/date';
import { useState } from 'react';

import { getNewYearMonthInfo, getYearMonthInfo } from '@utils/date';

export const useCalendar = () => {
  const currentDate = new Date();
  const currentYearMonth = getYearMonthInfo(currentDate);

  const [selectedDate, setSelectedDate] = useState<number>(currentDate.getDate());
  const [yearMonth, setYearMonth] = useState<YearMonth>(currentYearMonth);

  const handleDateClick = (date: number) => () => {
    setSelectedDate(date);
  };

  const handleYearMonthUpdate = (change: number) => () => {
    setSelectedDate(0);
    setYearMonth((prev) => getNewYearMonthInfo(prev, change));
  };

  return {
    currentDate,
    yearMonth,
    selectedDate,
    handleDateClick,
    handleYearMonthUpdate,
  };
};
