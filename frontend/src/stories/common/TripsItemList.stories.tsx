import { tripsMock } from '@mocks/data/trips';
import type { Meta, StoryObj } from '@storybook/react';

import { sortByStartDate } from '@utils/sortByStartDate';

import TripsItemList from '@components/trips/TripsItemList/TripsItemList';

const meta = {
  title: 'common/TripsItemList',
  component: TripsItemList,
  args: {
    trips: tripsMock,
    order: '등록순',
    changeSelect: (selectedId: string | number) => {},
  },
} satisfies Meta<typeof TripsItemList>;

export default meta;
type Story = StoryObj<typeof meta>;

export const SortBy_Register: Story = {};
export const SortBy_Date: Story = {
  render: () => {
    return (
      <TripsItemList
        trips={tripsMock?.slice().sort(sortByStartDate)}
        order="날짜순"
        changeSelect={(selectedId: string | number) => {}}
      />
    );
  },
};
