import type { Meta, StoryObj } from '@storybook/react';

import { useState } from 'react';

import GoogleMapWrapper from '@components/common/GoogleMapWrapper/GoogleMapWrapper';
import PlaceInput from '@components/trip/TripItemAddModal/PlaceInput/PlaceInput';

import type { TripItemFormData } from '@type/tripItem';

const meta = {
  title: 'trip/TripItemAddModal/PlaceInput',
  component: PlaceInput,
  argTypes: {
    value: { control: false },
    isUpdatable: { control: false },
    updateInputValue: { control: false },
  },
  args: {
    value: '',
    isError: false,
    isUpdatable: false,
    disableError: () => {},
  },
  decorators: [
    (Story) => (
      <GoogleMapWrapper>
        <Story />
      </GoogleMapWrapper>
    ),
  ],
} satisfies Meta<typeof PlaceInput>;

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

    return <PlaceInput {...args} value={value} updateInputValue={updateInputValue} />;
  },
};
