import type { Meta, StoryObj } from '@storybook/react';

import { useState } from 'react';

import MemoInput from '@components/trip/TripItemAddModal/MemoInput/MemoInput';

import type { TripItemFormData } from '@type/tripItem';

const meta = {
  title: 'trip/TripItemAddModal/MemoInput',
  component: MemoInput,
  args: {
    value: '',
  },
} satisfies Meta<typeof MemoInput>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {
  render: () => {
    const [value, setValue] = useState('');

    const updateInputValue = <K extends keyof TripItemFormData>(
      id: K,
      value: TripItemFormData[K]
    ) => {
      setValue(value as string);
    };

    return <MemoInput value={value} updateInputValue={updateInputValue} />;
  },
};
