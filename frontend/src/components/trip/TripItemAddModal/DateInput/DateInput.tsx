/* eslint-disable react/jsx-no-useless-fragment */
import type { ChangeEvent } from 'react';
import { memo, useEffect } from 'react';

import { Select } from 'hang-log-design-system';

import { selectStyling } from '@components/trip/TripItemAddModal/DateInput/DateInput.style';

import { useTripDates } from '@hooks/trip/useTripDates';

import { formatMonthDate } from '@utils/formatter';

import type { TripItemFormData } from '@type/tripItem';

interface DateInputProps {
  currentCategory: TripItemFormData['itemType'];
  tripId: number;
  dayLogId: number;
  updateInputValue: <K extends keyof TripItemFormData>(key: K, value: TripItemFormData[K]) => void;
}

const DateInput = ({ currentCategory, tripId, dayLogId, updateInputValue }: DateInputProps) => {
  const { dates } = useTripDates(tripId);

  useEffect(() => {
    const indexOfDayLogId = dates.findIndex((date) => date.id === dayLogId);

    if (indexOfDayLogId === dates.length - 1 || indexOfDayLogId === -1) {
      updateInputValue('dayLogId', dates[0].id);
    }
  }, [dates, dayLogId, updateInputValue]);

  const handleDateChange = (event: ChangeEvent<HTMLSelectElement>) => {
    updateInputValue('dayLogId', Number(event.target.value));
  };

  return (
    <Select
      css={selectStyling}
      label="날짜"
      id="date"
      name="date"
      required
      onChange={handleDateChange}
    >
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
