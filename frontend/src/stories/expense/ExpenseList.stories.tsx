import type { Meta, StoryObj } from '@storybook/react';

import ExpenseList from '@components/expense/ExpenseList/ExpenseList';

import { expense } from '@mocks/data/expense';

const meta = {
  title: 'expense/ExpenseList',
  component: ExpenseList,
  args: {
    items: expense.dayLogs[0].items,
  },
} satisfies Meta<typeof ExpenseList>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};

export const Empty: Story = {
  render: () => <ExpenseList.Empty tripId="1" />,
};
