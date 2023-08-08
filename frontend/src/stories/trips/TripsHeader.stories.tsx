import type { Meta, StoryObj } from '@storybook/react';

import TripsHeader from '@components/trips/TripsHeader/TripsHeader';

const meta = {
  title: 'trips/TripsHeader',
  component: TripsHeader,
} satisfies Meta<typeof TripsHeader>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
