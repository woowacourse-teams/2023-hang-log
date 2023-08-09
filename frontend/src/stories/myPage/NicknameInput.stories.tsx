import type { Meta, StoryObj } from '@storybook/react';

import NicknameInput from '@components/myPage/EditUserProfileForm/NicknameInput/NicknameInput';

const meta = {
  title: 'myPage/NicknameInput',
  component: NicknameInput,
} satisfies Meta<typeof NicknameInput>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
