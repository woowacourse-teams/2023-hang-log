import type { KeyboardEvent } from 'react';

import {
  dayContainerStyling,
  getDayInRangeStyling,
  getDayStyling,
  getDisabledDayStyling,
  getSelectedDayStyling,
  getTodayStyling,
} from '@components/Calendar/Day/Day.style';

export interface DayProps {
  /** 년 */
  year?: number | string;
  /** 월 */
  month?: number | string;
  /** 날짜 또는 요일 */
  day?: number | string;
  /** 날짜가 오늘인지에 대한 여부 */
  isToday?: boolean;
  /** 날짜가 선택되었는지에 대한 여부 */
  isSelected?: boolean;
  /** 날짜가 선택된 날짜 범위 안에 있는에 대한 여부 */
  isInRange?: boolean;
  /** 날짜 선택이 불가능한지에 대한 여부 */
  isDisabled?: boolean;
  /** 날짜를 클릭하면 발생할 이벤트 */
  onClick?: () => void;
}

const Day = ({
  year,
  month,
  day,
  isToday = false,
  isSelected = false,
  isInRange = false,
  isDisabled = false,
  onClick,
}: DayProps) => {
  const handleOptionKeyPress = (event: KeyboardEvent<HTMLInputElement>) => {
    if (event.key === 'Enter') {
      onClick?.();
    }
  };

  return (
    <div css={dayContainerStyling}>
      {day && (
        <span
          role={onClick ? 'button' : 'none'}
          css={[
            getDayStyling(!!onClick),
            getTodayStyling(isToday),
            getDayInRangeStyling(isInRange),
            getSelectedDayStyling(isSelected),
            getDisabledDayStyling(isDisabled),
          ]}
          tabIndex={onClick ? 0 : undefined}
          aria-label={year ? `${year}년 ${month}월 ${day}일` : `${day}요일`}
          onClick={onClick}
          onKeyDown={handleOptionKeyPress}
        >
          {day}
        </span>
      )}
    </div>
  );
};

export default Day;
