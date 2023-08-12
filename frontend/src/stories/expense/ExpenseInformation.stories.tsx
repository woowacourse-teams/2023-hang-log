import type { Meta, StoryObj } from '@storybook/react';

import ExpenseInformation from '@components/expense/ExpenseInformation/ExpenseInformation';

import { expense } from '@mocks/data/expense';

const meta = {
  title: 'expense/ExpenseInformation',
  component: ExpenseInformation,
  args: {
    tripId: 1,
    title: expense.title,
    startDate: expense.startDate,
    endDate: expense.endDate,
    cities: expense.cities,
  },
} satisfies Meta<typeof ExpenseInformation>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
