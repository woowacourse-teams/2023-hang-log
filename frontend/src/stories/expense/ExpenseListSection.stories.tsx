import type { Meta, StoryObj } from '@storybook/react';

import ExpenseListSection from '@components/expense/ExpenseListSection/ExpenseListSection';

const meta = {
  title: 'expense/ExpenseListSection',
  component: ExpenseListSection,
  argTypes: {
    tripId: { control: false },
  },
  args: {
    tripId: '1',
    isShared: false,
  },
} satisfies Meta<typeof ExpenseListSection>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
