import type { Meta, StoryObj } from '@storybook/react';

import Header from '@components/layout/Header/Header';

const meta = {
  title: 'layout/Header',
  component: Header,
} satisfies Meta<typeof Header>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
