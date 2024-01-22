import { useState } from 'react';
import type { KeyboardEvent } from 'react';

import { Box, DateRangePicker, Flex, Input, Label, Menu, useOverlay } from 'hang-log-design-system';

import {
  calendarStyling,
  containerStyling,
  getInputStyling,
} from '@components/common/DateInput/DateInput.style';

import { formatDateRange } from '@utils/formatter';

import type { DateRangeData } from '@type/trips';

import CalendarIcon from '@assets/svg/calendar-icon.svg';

interface DateInputProps {
  initialDateRange?: DateRangeData;
  updateDateInfo: (dateRange: DateRangeData) => void;
  required?: boolean;
}

const DateInput = ({
  initialDateRange = { startDate: null, endDate: null },
  updateDateInfo,
  required = false,
}: DateInputProps) => {
  const [inputValue, setInputValue] = useState(formatDateRange(initialDateRange));
  const [selectedDateRange, setSelectedDateRange] = useState(initialDateRange);
  const {
    isOpen: isCalendarOpen,
    open: openCalendar,
    close: closeCalendar,
    toggle: toggleCalendar,
  } = useOverlay();

  const handleDateClick = (dateRange: DateRangeData) => {
    if (!dateRange.endDate) return;

    setSelectedDateRange(dateRange);
    setInputValue(formatDateRange(dateRange));
    updateDateInfo(dateRange);
  };

  const handleEnter = (e: KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') {
      openCalendar();
    }
  };

  return (
    <Flex styles={{ direction: 'column', width: '100%', margin: '0 auto', align: 'flex-start' }}>
      <Label required={required}>방문 기간</Label>
      <Menu closeMenu={closeCalendar} css={containerStyling}>
        <Box onClick={toggleCalendar} css={getInputStyling(isCalendarOpen)}>
          <Input
            id="date"
            placeholder="방문 날짜를 입력해주세요"
            icon={<CalendarIcon aria-label="캘린더 아이콘" />}
            value={inputValue}
            onKeyDown={handleEnter}
            readOnly
          />
        </Box>
        {isCalendarOpen && (
          <Box css={calendarStyling}>
            <DateRangePicker
              onDateSelect={handleDateClick}
              maxDateRange={60}
              hasRangeRestriction
              initialSelectedDateRange={
                selectedDateRange.startDate !== null ? selectedDateRange : undefined
              }
            />
          </Box>
        )}
      </Menu>
    </Flex>
  );
};

export default DateInput;
