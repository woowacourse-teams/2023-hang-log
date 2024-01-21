import type { Meta, StoryObj } from '@storybook/react';

import Label from '@components/Label/Label';

const meta = {
  title: 'Label',
  component: Label,
  argTypes: {
    children: {
      control: { type: 'text' },
    },
    required: {
      control: { type: 'boolean' },
    },
  },
  args: {
    children: 'Label',
    required: false,
  },
} satisfies Meta<typeof Label>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
