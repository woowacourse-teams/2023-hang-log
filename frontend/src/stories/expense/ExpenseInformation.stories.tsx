import type { Meta, StoryObj } from '@storybook/react';

import ExpenseInformation from '@components/expense/ExpenseInformation/ExpenseInformation';

import { useExpenseQuery } from '@hooks/api/useExpenseQuery';

const meta = {
  title: 'expense/ExpenseInformation',
  component: ExpenseInformation,
  args: {
    tripId: '1',
    isShared: false,
    isPublished: false,
  },
  decorators: [
    (Story) => {
      useExpenseQuery('1', { isShared: false, isPublished: false });

      return <Story />;
    },
  ],
} satisfies Meta<typeof ExpenseInformation>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
