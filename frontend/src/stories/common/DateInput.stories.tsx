import type { Meta, StoryObj } from '@storybook/react';

import DateInput from '@components/common/DateInput/DateInput';

const meta = {
  title: 'common/DateInput',
  component: DateInput,
  args: {
    initialDateRange: { startDate: null, endDate: null },
    updateDateInfo: () => {},
  },
} satisfies Meta<typeof DateInput>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};

export const InitialDateRage: Story = {
  args: {
    initialDateRange: { startDate: '2023-03-12', endDate: '2023-04-01' },
  },
};
