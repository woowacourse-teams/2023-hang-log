import { trip } from '@mocks/data/trip';
import type { Meta, StoryObj } from '@storybook/react';
import { useSelect } from 'hang-log-design-system';

import { useTripQuery } from '@hooks/api/useTripQuery';

import DayLogList from '@components/common/DayLogList/DayLogList';

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
    useTripQuery(1);
    const { selected, handleSelectClick } = useSelect(trip.dayLogs[0].id);
    const selectedDayLog = trip.dayLogs.find((log) => log.id === selected)!;

    return (
      <DayLogList
        tripId={1}
        selectedDayLog={selectedDayLog}
        onTabChange={handleSelectClick}
        openAddModal={() => {}}
      />
    );
  },
  args: {
    tripId: 1,
    selectedDayLog: trip.dayLogs[0],
    onTabChange: () => {},
  },
};
