import type { Meta, StoryObj } from '@storybook/react';

import StarRating from '@components/common/StarRating/StarRating';

const meta = {
  title: 'common/StarRating',
  component: StarRating,
  argTypes: {
    rate: { control: 'number' },
  },
  args: {
    rate: 3.5,
  },
} satisfies Meta<typeof StarRating>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
