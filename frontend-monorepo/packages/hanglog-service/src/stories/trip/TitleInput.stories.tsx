import type { Meta, StoryObj } from '@storybook/react';

import { useState } from 'react';

import TitleInput from '@components/trip/TripItemAddModal/TitleInput/TitleInput';

import type { TripItemFormData } from '@type/tripItem';

const meta = {
  title: 'trip/TripItemAddModal/TitleInput',
  component: TitleInput,
  argTypes: {
    value: { control: false },
    updateInputValue: { control: false },
  },
  args: {
    value: '',
    isError: false,
    disableError: () => {},
  },
} satisfies Meta<typeof TitleInput>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {
  render: ({ ...args }) => {
    const [value, setValue] = useState('');

    const updateInputValue = <K extends keyof TripItemFormData>(
      id: K,
      value: TripItemFormData[K]
    ) => {
      setValue(value as string);
    };

    return <TitleInput {...args} value={value} updateInputValue={updateInputValue} />;
  },
};
