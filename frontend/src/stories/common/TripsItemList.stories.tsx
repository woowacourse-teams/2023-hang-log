import { trips } from '@mocks/data/trips';
import type { Meta, StoryObj } from '@storybook/react';

import { sortByStartDate } from '@utils/sortByStartDate';

import TripsItemList from '@components/trips/TripsItemList/TripsItemList';

const meta = {
  title: 'common/TripsItemList',
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
