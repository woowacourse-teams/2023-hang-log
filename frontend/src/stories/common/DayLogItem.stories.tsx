import { trip } from '@mocks/data/trip';
import type { Meta, StoryObj } from '@storybook/react';

import DayLogItem from '@components/common/DayLogItem/DayLogItem';

const meta = {
  title: 'common/DayLogItem',
  component: DayLogItem,
  argTypes: {
    tripId: { control: false },
  },
  args: {
    tripId: 1,
    ...trip.dayLogs[0],
  },
} satisfies Meta<typeof DayLogItem>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
