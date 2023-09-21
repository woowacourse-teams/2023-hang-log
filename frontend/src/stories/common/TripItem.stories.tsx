import type { Meta, StoryObj } from '@storybook/react';

import TripItem from '@components/common/TripItem/TripItem';

import { trip } from '@mocks/data/trip';

const meta = {
  title: 'common/TripItem',
  component: TripItem,
  args: {
    tripId: '1',
    dayLogId: 1,
  },
} satisfies Meta<typeof TripItem>;

export default meta;
type Story = StoryObj<typeof meta>;

export const WithImage: Story = {
  args: { ...trip.dayLogs[0].items[0] },
};

export const WithoutImage: Story = {
  args: { ...trip.dayLogs[0].items[2] },
};
