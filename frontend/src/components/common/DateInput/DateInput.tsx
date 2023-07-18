import { dateRangeToString } from '@/utils/converter';
import CalendarIcon from '@assets/svg/calendar-icon.svg';
import { DateRange } from '@type/trip';
import {
  Box,
  DateRangePicker,
  Flex,
  Heading,
  Input,
  Menu,
  useOverlay,
} from 'hang-log-design-system';
import { useState } from 'react';

import {
  calendarStyling,
  containerStyling,
  inputStyling,
} from '@components/common/DateInput/DateInput.style';

interface DateInputProps {
  initialDateRange?: DateRange;
}

const DateInput = ({ initialDateRange = { start: null, end: null } }: DateInputProps) => {
  const [inputValue, setInputValue] = useState(dateRangeToString(initialDateRange));
  const [selectedDateRange, setSelectedDateRange] = useState(initialDateRange);
  const { isOpen: isCalendarOpen, close: closeCalendar, toggle: toggleCalendar } = useOverlay();

  const handleDateClick = (dateRange: DateRange) => {
    if (!dateRange.end) return;

    setSelectedDateRange(dateRange);
    setInputValue(dateRangeToString(dateRange));
  };

  return (
    <Flex styles={{ direction: 'column', width: '40%', margin: '0 auto', align: 'flex-start' }}>
      <Heading size="xSmall">방문 기간</Heading>
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
