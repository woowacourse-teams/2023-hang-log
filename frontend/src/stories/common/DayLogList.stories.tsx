import type { Meta, StoryObj } from '@storybook/react';

import { useSelect } from 'hang-log-design-system';

import DayLogList from '@components/common/DayLogList/DayLogList';

import { useTripQuery } from '@hooks/api/useTripQuery';

import { trip } from '@mocks/data/trip';

const meta = {
  title: 'common/DayLogList',
  component: DayLogList,
  argTypes: {
    tripId: { control: false },
    selectedDayLog: { control: false },
  },
} satisfies Meta<typeof DayLogList>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {
  render: () => {
    useTripQuery('PERSONAL', '1');
    const { selected, handleSelectClick } = useSelect(trip.dayLogs[0].id);
    const selectedDayLog = trip.dayLogs.find((log) => log.id === selected)!;

    return (
      <DayLogList
        tripId="1"
        tripType="PERSONAL"
        selectedDayLog={selectedDayLog}
        onTabChange={handleSelectClick}
        openAddModal={() => {}}
      />
    );
  },
  args: {
    tripId: '1',
    tripType: 'PERSONAL',
    selectedDayLog: trip.dayLogs[0],
    onTabChange: () => {},
  },
};
