import { trip } from '@mocks/data/trip';
import type { Meta, StoryObj } from '@storybook/react';

import DayLogList from '@components/common/DayLogList/DayLogList';

const meta = {
  title: 'common/DayLogList',
  component: DayLogList,
  argTypes: {
    tripId: { control: false },
  },
  args: {
    tripId: 1,
    logs: [...trip.dayLogs],
  },
} satisfies Meta<typeof DayLogList>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
