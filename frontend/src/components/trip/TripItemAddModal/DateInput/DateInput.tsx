/* eslint-disable react/jsx-no-useless-fragment */
import type { TripItemFormData } from '@type/tripItem';
import { Select } from 'hang-log-design-system';
import type { ChangeEvent } from 'react';
import { memo } from 'react';

import { formatMonthDate } from '@utils/formatter';

interface DateInputProps {
  currentCategory: TripItemFormData['itemType'];
  dayLogId: number;
  dates: { id: number; date: string }[];
  updateInputValue: <K extends keyof TripItemFormData>(key: K, value: TripItemFormData[K]) => void;
}

const DateInput = ({ currentCategory, dayLogId, dates, updateInputValue }: DateInputProps) => {
  const handleDateChange = (event: ChangeEvent<HTMLSelectElement>) => {
    updateInputValue('dayLogId', Number(event.target.value));
  };

  return (
    <Select label="날짜" name="date" required onChange={handleDateChange}>
      <>
        {Array.from({ length: dates.length - 1 }, (_, index) => (
          <option
            key={dates[index].id}
            value={dates[index].id}
            selected={dayLogId === dates[index].id}
          >
            Day {index + 1} - {formatMonthDate(dates[index].date)}
          </option>
        ))}
      </>
      <>
        {!currentCategory && (
          <option
            key={dates.at(-1)?.id}
            value={dates.at(-1)?.id}
            selected={dayLogId === dates.at(-1)?.id}
          >
            기타
          </option>
        )}
      </>
    </Select>
  );
};

export default memo(DateInput);
