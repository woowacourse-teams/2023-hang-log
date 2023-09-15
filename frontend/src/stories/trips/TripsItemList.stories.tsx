import type { Meta, StoryObj } from '@storybook/react';

import TripsItemList from '@components/trips/TripsItemList/TripsItemList';

import { sortByStartDate } from '@utils/sort';

import { trips } from '@mocks/data/myTrips';

const meta = {
  title: 'trips/TripsItemList',
  component: TripsItemList,
  args: {
    trips,
    order: '등록순',
    changeSelect: () => {},
  },
} satisfies Meta<typeof TripsItemList>;

export default meta;
type Story = StoryObj<typeof meta>;

export const SortByRegister: Story = {};

export const SortByDate: Story = {
  render: ({ ...args }) => {
    return <TripsItemList {...args} trips={trips?.slice().sort(sortByStartDate)} order="날짜순" />;
  },
};
