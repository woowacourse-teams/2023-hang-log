import type { Meta, StoryObj } from '@storybook/react';

import ExpenseItem from '@components/expense/ExpenseItem/ExpenseItem';

import { expense } from '@mocks/data/expense';

const meta = {
  title: 'expense/ExpenseItem',
  component: ExpenseItem,
  args: {
    ...expense.dayLogs[0].items[0],
  },
} satisfies Meta<typeof ExpenseItem>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
