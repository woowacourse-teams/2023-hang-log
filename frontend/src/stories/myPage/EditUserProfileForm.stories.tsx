import { userInfo } from '@mocks/data/member';
import type { Meta, StoryObj } from '@storybook/react';

import EditUserProfileForm from '@components/myPage/EditUserProfileForm/EditUserProfileForm';

const meta = {
  title: 'myPage/EditUserProfileForm',
  component: EditUserProfileForm,
  args: {
    initialData: userInfo,
  },
} satisfies Meta<typeof EditUserProfileForm>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
