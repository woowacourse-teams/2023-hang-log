import type { Meta, StoryObj } from '@storybook/react';

import TripsItemList from '@components/trips/TripsItemList/TripsItemList';

const meta = {
  title: 'common/TripsItemList-Empty',
  component: TripsItemList.Empty,
  args: {},
} satisfies Meta<typeof TripsItemList>;

export default meta;
type Story = StoryObj<typeof meta>;

export const SortBy_Register: Story = {};
