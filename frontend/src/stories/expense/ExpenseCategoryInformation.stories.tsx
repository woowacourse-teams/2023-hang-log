import type { Meta, StoryObj } from '@storybook/react';

import ExpenseCategoryInformation from '@components/expense/ExpenseCategoryInformation/ExpenseCategoryInformation';

import { useExpenseQuery } from '@hooks/api/useExpenseQuery';

const meta = {
  title: 'expense/ExpenseCategoryInformation',
  component: ExpenseCategoryInformation,
  args: {
    tripId: 1,
  },
  decorators: [
    (Story) => {
      useExpenseQuery(1);

      return <Story />;
    },
  ],
} satisfies Meta<typeof ExpenseCategoryInformation>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
