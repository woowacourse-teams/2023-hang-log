import type { Meta, StoryObj } from '@storybook/react';

import TotalExpenseSection from '@components/expense/TotalExpenseSection/TotalExpenseSection';

import { useExpenseQuery } from '@hooks/api/useExpenseQuery';

const meta = {
  title: 'expense/TotalExpenseSection',
  component: TotalExpenseSection,
  argTypes: {
    tripId: { control: false },
  },
  args: {
    tripId: '1',
    isShared: false,
  },
  decorators: [
    (Story) => {
      useExpenseQuery('1');

      return <Story />;
    },
  ],
} satisfies Meta<typeof TotalExpenseSection>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
