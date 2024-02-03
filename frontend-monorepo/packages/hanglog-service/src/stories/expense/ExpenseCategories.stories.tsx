import type { Meta, StoryObj } from '@storybook/react';

import ExpenseCategories from '@components/expense/ExpenseCategories/ExpenseCategories';

import { useExpenseQuery } from '@hooks/api/useExpenseQuery';

const meta = {
  title: 'expense/ExpenseCategories',
  component: ExpenseCategories,
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
} satisfies Meta<typeof ExpenseCategories>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
