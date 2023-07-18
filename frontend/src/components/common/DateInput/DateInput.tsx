import CalendarIcon from '@assets/svg/calendar-icon.svg';
import { DateRangeData } from '@type/trips';
import { Box, DateRangePicker, Flex, Input, Label, Menu, useOverlay } from 'hang-log-design-system';
import { useState } from 'react';

import { dateRangeToString } from '@utils/formatter';

import {
  calendarStyling,
  containerStyling,
  inputStyling,
} from '@components/common/DateInput/DateInput.style';

interface DateInputProps {
  initialDateRange?: DateRangeData;
}

const DateInput = ({ initialDateRange = { start: null, end: null } }: DateInputProps) => {
  const [inputValue, setInputValue] = useState(dateRangeToString(initialDateRange));
  const [selectedDateRange, setSelectedDateRange] = useState(initialDateRange);
  const { isOpen: isCalendarOpen, close: closeCalendar, toggle: toggleCalendar } = useOverlay();

  const handleDateClick = (dateRange: DateRangeData) => {
    if (!dateRange.end) return;

    setSelectedDateRange(dateRange);
    setInputValue(dateRangeToString(dateRange));
  };

  return (
    <Flex styles={{ direction: 'column', width: '40%', margin: '0 auto', align: 'flex-start' }}>
      <Label>방문 기간</Label>
      <Menu closeMenu={closeCalendar} css={containerStyling}>
        <Box onClick={toggleCalendar} css={inputStyling}>
          <Input
            placeholder="방문 날짜를 입력해주세요"
            icon={<CalendarIcon aria-label="캘린더 아이콘" />}
            disabled
            value={inputValue}
          />
        </Box>
        {isCalendarOpen && (
          <Box css={calendarStyling}>
            <DateRangePicker
              onDateSelect={handleDateClick}
              maxDateRange={60}
              hasRangeRestriction={true}
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
