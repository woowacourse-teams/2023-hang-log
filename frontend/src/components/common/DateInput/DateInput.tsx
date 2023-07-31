import CalendarIcon from '@assets/svg/calendar-icon.svg';
import type { DateRangeData } from '@type/trips';
import { Box, DateRangePicker, Flex, Input, Label, Menu, useOverlay } from 'hang-log-design-system';
import { useEffect, useState } from 'react';

import { formatDateRange } from '@utils/formatter';

import {
  calendarStyling,
  containerStyling,
  getInputStyling,
} from '@components/common/DateInput/DateInput.style';

interface DateInputProps {
  initialDateRange?: DateRangeData;
  updateDateInfo: (dateRange: DateRangeData) => void;
  required?: boolean;
}

const DateInput = ({
  initialDateRange = { start: null, end: null },
  updateDateInfo,
  required = false,
}: DateInputProps) => {
  const [inputValue, setInputValue] = useState(formatDateRange(initialDateRange));
  const [selectedDateRange, setSelectedDateRange] = useState(initialDateRange);
  const { isOpen: isCalendarOpen, close: closeCalendar, toggle: toggleCalendar } = useOverlay();

  useEffect(() => {
    updateDateInfo(selectedDateRange);
  }, [selectedDateRange, updateDateInfo]);

  const handleDateClick = (dateRange: DateRangeData) => {
    if (!dateRange.end) return;

    setSelectedDateRange(dateRange);
    setInputValue(formatDateRange(dateRange));
  };

  return (
    <Flex styles={{ direction: 'column', width: '400px', margin: '0 auto', align: 'flex-start' }}>
      <Label required={required}>방문 기간</Label>
      <Menu closeMenu={closeCalendar} css={containerStyling}>
        <Box onClick={toggleCalendar} css={getInputStyling(isCalendarOpen)}>
          <Input
            placeholder="방문 날짜를 입력해주세요"
            icon={<CalendarIcon aria-label="캘린더 아이콘" />}
            value={inputValue}
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
                selectedDateRange.start !== null ? selectedDateRange : undefined
              }
            />
          </Box>
        )}
      </Menu>
    </Flex>
  );
};

export default DateInput;
