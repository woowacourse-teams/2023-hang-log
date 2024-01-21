import { containerStyle, informationStyle } from '@stories/styles';
import type { Meta, StoryObj } from '@storybook/react';

import Day from '@components/Calendar/Day/Day';

const meta = {
  title: 'Day',
  component: Day,
  argTypes: {
    day: {
      day: { type: 'number', min: 1, max: 31 },
    },
    isToday: {
      control: { type: 'boolean' },
    },
    isSelected: {
      control: { type: 'boolean' },
    },
    isInRange: {
      control: { type: 'boolean' },
    },
    isDisabled: {
      control: { type: 'boolean' },
    },
  },
  args: {
    day: 13,
    isToday: false,
    isSelected: false,
    isInRange: false,
    isDisabled: false,
  },
} satisfies Meta<typeof Day>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Playground: Story = {};

export const Variants: Story = {
  render: ({ ...args }) => {
    return (
      <ul css={containerStyle}>
        <li css={informationStyle}>
          <h6>Default</h6>
          <Day {...args} />
        </li>
        <li css={informationStyle}>
          <h6>Today</h6>
          <Day {...args} isToday />
        </li>
        <li css={informationStyle}>
          <h6>Selected Day</h6>
          <Day {...args} isSelected />
        </li>
        <li css={informationStyle}>
          <h6>Day in Range</h6>
          <Day {...args} isInRange />
        </li>
        <li css={informationStyle}>
          <h6>Disabled Day</h6>
          <Day {...args} isDisabled />
        </li>
      </ul>
    );
  },
  argTypes: {
    isToday: { control: false },
    isSelected: { control: false },
    isInRange: { control: false },
    isDisabled: { control: false },
  },
};

export const Default: Story = {
  argTypes: {
    isToday: { control: false },
    isSelected: { control: false },
    isInRange: { control: false },
    isDisabled: { control: false },
  },
};

export const Today: Story = {
  argTypes: {
    isToday: { control: false },
    isSelected: { control: false },
    isInRange: { control: false },
    isDisabled: { control: false },
  },
  args: {
    isToday: true,
  },
};

export const SelectedDay: Story = {
  argTypes: {
    isToday: { control: false },
    isSelected: { control: false },
    isInRange: { control: false },
    isDisabled: { control: false },
  },
  args: {
    isSelected: true,
  },
};

export const DayInRange: Story = {
  argTypes: {
    isToday: { control: false },
    isSelected: { control: false },
    isInRange: { control: false },
    isDisabled: { control: false },
  },
  args: {
    isInRange: true,
  },
};

export const DisabledDay: Story = {
  argTypes: {
    isToday: { control: false },
    isSelected: { control: false },
    isInRange: { control: false },
    isDisabled: { control: false },
  },
  args: {
    isDisabled: true,
  },
};
