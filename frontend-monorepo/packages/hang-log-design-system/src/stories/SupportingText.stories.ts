import type { Meta, StoryObj } from '@storybook/react';

import SupportingText from '@components/SupportingText/SupportingText';

const meta = {
  title: 'SupportingText',
  component: SupportingText,
  argTypes: {
    children: {
      control: { type: 'text' },
    },
    isError: {
      control: { type: 'boolean' },
    },
  },
  args: {
    children: 'Supporting text',
    isError: false,
  },
} satisfies Meta<typeof SupportingText>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Playground: Story = {};

export const Default: Story = {
  argTypes: {
    isError: {
      control: false,
    },
  },
};

export const Error: Story = {
  args: {
    isError: true,
  },
  argTypes: {
    isError: {
      control: false,
    },
  },
};
