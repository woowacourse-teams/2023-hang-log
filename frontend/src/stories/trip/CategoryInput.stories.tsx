import type { Meta, StoryObj } from '@storybook/react';
import type { TripItemFormData } from '@type/tripItem';
import { useState } from 'react';

import CategoryInput from '@components/trip/TripItemAddModal/CategoryInput/CategoryInput';

const meta = {
  title: 'trip/TripItemAddModal/CategoryInput',
  component: CategoryInput,
  argTypes: {
    itemType: { control: false },
    updateInputValue: { control: false },
    disableError: { control: false },
  },
  args: {
    itemType: true,
  },
} satisfies Meta<typeof CategoryInput>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {
  render: () => {
    const [value, setValue] = useState(true);

    const updateInputValue = <K extends keyof TripItemFormData>(
      id: K,
      value: TripItemFormData[K]
    ) => {
      setValue(value as boolean);
    };

    return (
      <CategoryInput itemType={value} updateInputValue={updateInputValue} disableError={() => {}} />
    );
  },
};
