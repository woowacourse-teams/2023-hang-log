import type { Meta, StoryObj } from '@storybook/react';

import ProfileImageInput from '@components/myPage/EditUserProfileForm/ProfileImageInput/ProfileImageInput';

import { images } from '@mocks/data/image';

const meta = {
  title: 'myPage/ProfileImageInput',
  component: ProfileImageInput,
  args: {
    initialImageUrl: images[0],
  },
} satisfies Meta<typeof ProfileImageInput>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
