import type { Meta, StoryObj } from '@storybook/react';

import { useState } from 'react';

import StarRatingInput from '@components/trip/TripItemAddModal/StarRatingInput/StarRatingInput';

import type { StarRatingData, TripItemFormData } from '@type/tripItem';

const meta = {
  title: 'trip/TripItemAddModal/StarRatingInput',
  component: StarRatingInput,
  args: {
    rating: 0,
    isMobile: false,
  },
} satisfies Meta<typeof StarRatingInput>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {
  render: ({ ...args }) => {
    const [value, setValue] = useState<StarRatingData>(0);

    const updateInputValue = <K extends keyof TripItemFormData>(
      id: K,
      value: TripItemFormData[K]
    ) => {
      setValue(value as StarRatingData);
    };

    return (
      <StarRatingInput
        isMobile={args.isMobile}
        rating={value}
        updateInputValue={updateInputValue}
      />
    );
  },
};
