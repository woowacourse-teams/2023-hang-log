import type { Meta, StoryObj } from '@storybook/react';

import DateInput from '@components/trip/TripItemAddModal/DateInput/DateInput';

const meta = {
  title: 'trip/TripItemAddModal/DateInput',
  component: DateInput,
  args: {
    currentCategory: true,
    dayLogId: 1,
    dates: [
      { id: 1, date: '2023-03-12' },
      { id: 2, date: '2023-03-13' },
      { id: 3, date: '2023-03-14' },
      { id: 4, date: '2023-03-15' },
    ],
  },
} satisfies Meta<typeof DateInput>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
