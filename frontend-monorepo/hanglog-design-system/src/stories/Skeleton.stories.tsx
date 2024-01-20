import { containerStyle } from '@stories/styles';
import type { Meta, StoryObj } from '@storybook/react';

import Skeleton from '@components/Skeleton/Skeleton';

const meta = {
  title: 'Skeleton',
  component: Skeleton,
  args: {
    width: '500px',
    height: '24px',
  },
} satisfies Meta<typeof Skeleton>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Playground: Story = {};

export const Default: Story = {
  argTypes: {
    width: { control: false },
    height: { control: false },
    variant: { control: false },
  },
};

export const Image: Story = {
  args: {
    width: '450px',
    height: '300px',
  },
};

export const Paragraph: Story = {
  args: {
    width: '400px',
    height: '100px',
  },
};

export const Circle: Story = {
  args: {
    width: '200px',
    variant: 'circle',
  },
  argTypes: {
    variant: { control: false },
  },
};

export const Combination: Story = {
  render: () => {
    return (
      <div css={containerStyle}>
        <Skeleton variant="circle" width="100px" />
        <Skeleton width="300px" height="100px" />
        <Skeleton />
        <Skeleton />
      </div>
    );
  },
  argTypes: {
    variant: { control: false },
    width: { control: false },
    height: { control: false },
  },
};
