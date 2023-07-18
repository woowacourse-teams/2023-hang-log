import { trip } from '@mocks/data/trip';
import type { Meta, StoryObj } from '@storybook/react';

import TripItemList from '@components/common/TripItemList/TripItemList';

const meta = {
  title: 'common/TripItemList',
  component: TripItemList,
  argTypes: {
    tripId: { control: false },
    dayLogId: { control: false },
  },
  args: {
    tripId: 1,
    dayLogId: 1,
    tripItems: [...trip.dayLogs[0].items],
  },
} satisfies Meta<typeof TripItemList>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
