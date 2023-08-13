import type { Meta, StoryObj } from '@storybook/react';

import { useState } from 'react';

import NicknameInput from '@components/myPage/EditUserProfileForm/NicknameInput/NicknameInput';

import type { UserData } from '@type/member';

const meta = {
  title: 'myPage/NicknameInput',
  component: NicknameInput,
  argTypes: {
    value: { control: false },
    updateInputValue: { control: false },
  },
  args: {
    value: '',
    isError: false,
    disableError: () => {},
  },
} satisfies Meta<typeof NicknameInput>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {
  render: ({ ...args }) => {
    const [value, setValue] = useState('');

    const updateInputValue = <K extends keyof UserData>(id: K, value: UserData[K]) => {
      setValue(value as string);
    };

    return <NicknameInput {...args} value={value} updateInputValue={updateInputValue} />;
  },
};
