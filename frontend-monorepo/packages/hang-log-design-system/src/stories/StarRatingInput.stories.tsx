import type { Meta, StoryObj } from '@storybook/react';

import { useStarRatingInput } from '@hooks/useStarRatingInput';

import StarRatingInput from '@components/StarRatingInput/StarRatingInput';

const meta = {
  title: 'StarRatingInput',
  component: StarRatingInput,
  argTypes: {
    rate: {
      control: false,
    },
    size: {
      control: { type: 'number' },
      description: 'size',
    },
    gap: {
      control: { type: 'number' },
      description: 'gap',
    },
    isMobile: {
      control: { type: 'boolean' },
      description: 'isMobile',
    },
  },
  args: {
    isMobile: true,
    label: '별점',
    supportingText: '별점을 입력해 주세요',
    rate: 1,
    size: 24,
    gap: 2,
  },
} satisfies Meta<typeof StarRatingInput>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Playground: Story = {
  render: ({ size, gap, ...args }) => {
    const { starRate, handleStarClick, handleStarHover, handleStarHoverOut } =
      useStarRatingInput(2.5);

    return (
      <StarRatingInput
        {...args}
        rate={starRate}
        onStarClick={handleStarClick}
        onStarHover={handleStarHover}
        onStarHoverOut={handleStarHoverOut}
        size={size}
        gap={gap}
      />
    );
  },
};
