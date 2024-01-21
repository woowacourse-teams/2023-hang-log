import type { Meta, StoryObj } from '@storybook/react';

import Spinner from '@components/Spinner/Spinner';

const meta = {
  title: 'Spinner',
  component: Spinner,
  args: {
    timing: 1,
    size: 50,
    width: 5,
    disabled: false,
  },
} satisfies Meta<typeof Spinner>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};

export const Disabled: Story = {
  args: {
    disabled: true,
  },
};
