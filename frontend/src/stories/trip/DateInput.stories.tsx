import type { Meta, StoryObj } from '@storybook/react';

import DateInput from '@components/trip/TripItemAddModal/DateInput/DateInput';

import { useTripQuery } from '@hooks/api/useTripQuery';

const meta = {
  title: 'trip/TripItemAddModal/DateInput',
  component: DateInput,
  argTypes: {
    dayLogId: { control: false },
    updateInputValue: { control: false },
  },
  args: {
    currentCategory: true,
    tripId: '1',
    dayLogId: 1,
  },
  decorators: [
    (Story) => {
      useTripQuery('PERSONAL', '1');

      return <Story />;
    },
  ],
} satisfies Meta<typeof DateInput>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
