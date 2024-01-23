import type { Meta, StoryObj } from '@storybook/react';

import { useCalendar } from '@hooks/useCalendar';

import Calendar from '@components/Calendar/Calendar';

const meta = {
  title: 'Calendar',
  component: Calendar,
} satisfies Meta<typeof Calendar>;

export default meta;
type Story = StoryObj<typeof Calendar>;

export const Default: Story = {
  render: () => {
    const { currentDate, yearMonth, selectedDate } = useCalendar();

    return (
      <Calendar currentDate={currentDate} yearMonthData={yearMonth} selectedDate={selectedDate} />
    );
  },
};

export const Clickable: Story = {
  render: () => {
    const { currentDate, yearMonth, selectedDate, handleDateClick } = useCalendar();

    return (
      <Calendar
        currentDate={currentDate}
        yearMonthData={yearMonth}
        selectedDate={selectedDate}
        onDateClick={handleDateClick}
      />
    );
  },
};
