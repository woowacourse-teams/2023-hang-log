import type { Meta, StoryObj } from '@storybook/react';

import TripsItem from '@components/trips/TripsItem/TripsItem';

import { formatDate } from '@utils/formatter';

import { trips } from '@mocks/data/myTrips';

const meta = {
  title: 'trips/TripsItem',
  component: TripsItem,
  args: {
    id: 1,
    index: 0,
    itemName: trips[0].title,
    cityTags: trips[0].cities,
    coverImage: trips[0].imageName,
    duration: `${formatDate(trips[0].startDate)} - ${formatDate(trips[0].endDate)}`,
    description: trips[0].description,
  },
} satisfies Meta<typeof TripsItem>;

export default meta;
type Story = StoryObj<typeof meta>;

export const WithDescription: Story = {};

export const WithoutDescription: Story = {
  args: {
    description: null,
  },
};
