import type { TripItemFormType } from '@type/tripItem';
import { Select } from 'hang-log-design-system';
import type { ChangeEvent } from 'react';
import { memo } from 'react';

import { formatMonthDate } from '@utils/formatter';

interface TripItemDateInputProps {
  currentCategory: TripItemFormType['itemType'];
  dayLogIds: number[];
  initialDate: string;
  dates: string[];
  updateInputValue: <K extends keyof TripItemFormType>(key: K, value: TripItemFormType[K]) => void;
}

const TripItemDateInput = ({
  currentCategory,
  dayLogIds,
  initialDate,
  dates,
  updateInputValue,
}: TripItemDateInputProps) => {
  const handleDateChange = (event: ChangeEvent<HTMLSelectElement>) => {
    updateInputValue('dayLogId', Number(event.target.value));
  };

  return (
    <Select label="날짜" name="date" required onChange={handleDateChange}>
      <>
        {Array.from({ length: dates.length - 1 }, (_, index) => (
          <option
            key={dates[index]}
            value={dayLogIds[index]}
            selected={initialDate === dates[index]}
          >
            Day {index + 1} - {formatMonthDate(dates[index])}
          </option>
        ))}
      </>
      <>
        {!currentCategory && (
          <option
            key={dates.at(-1)}
            value={dayLogIds.at(-1)}
            selected={initialDate === dates.at(-1)}
          >
            기타
          </option>
        )}
      </>
    </Select>
  );
};

export default memo(TripItemDateInput);
