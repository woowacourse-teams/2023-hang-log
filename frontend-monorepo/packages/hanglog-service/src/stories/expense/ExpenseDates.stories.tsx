import type { Meta, StoryObj } from '@storybook/react';

import ExpenseDates from '@components/expense/ExpenseDates/ExpenseDates';

import { useExpenseQuery } from '@hooks/api/useExpenseQuery';

const meta = {
  title: 'expense/ExpenseDates',
  component: ExpenseDates,
  args: {
    tripId: '1',
    tripType: 'PERSONAL',
  },
  decorators: [
    (Story) => {
      useExpenseQuery('1', 'PERSONAL');

      return <Story />;
    },
  ],
} satisfies Meta<typeof ExpenseDates>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
