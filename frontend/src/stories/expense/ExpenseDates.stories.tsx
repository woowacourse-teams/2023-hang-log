import type { Meta, StoryObj } from '@storybook/react';

import ExpenseDates from '@components/expense/ExpenseDates/ExpenseDates';

import { useExpenseQuery } from '@hooks/api/useExpenseQuery';

const meta = {
  title: 'expense/ExpenseDates',
  component: ExpenseDates,
  args: {
    tripId: '1',
  },
  decorators: [
    (Story) => {
      useExpenseQuery('1', { isShared: false, isPublished: false });

      return <Story />;
    },
  ],
} satisfies Meta<typeof ExpenseDates>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
