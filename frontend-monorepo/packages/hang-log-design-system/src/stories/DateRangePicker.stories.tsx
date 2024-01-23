import { DEFAULT_MAX_DATE_RANGE } from '@constants/index';
import type { Meta, StoryObj } from '@storybook/react';

import DateRangePicker from '@components/DateRangePicker/DateRangePicker';

const meta = {
  title: 'DateRangePicker',
  component: DateRangePicker,
  argTypes: {
    onDateSelect: { control: false },
  },
} satisfies Meta<typeof DateRangePicker>;

export default meta;
type Story = StoryObj<typeof DateRangePicker>;

export const Default: Story = {};

export const FutureDaysDisabled: Story = {
  args: {
    isFutureDaysRestricted: true,
  },
};

export const DaysDisabled: Story = {
  args: {
    hasRangeRestriction: true,
    maxDateRange: DEFAULT_MAX_DATE_RANGE,
  },
};

export const InitialSelectedDateRange = {
  args: {
    initialSelectedDateRange: {
      start: '2023-07-12',
      end: '2023-07-30',
    },
  },
};
