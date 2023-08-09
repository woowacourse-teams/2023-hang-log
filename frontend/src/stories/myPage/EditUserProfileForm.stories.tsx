import type { Meta, StoryObj } from '@storybook/react';

import EditUserProfileForm from '@components/myPage/EditUserProfileForm/EditUserProfileForm';

const meta = {
  title: 'myPage/EditUserProfileForm',
  component: EditUserProfileForm,
} satisfies Meta<typeof EditUserProfileForm>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
