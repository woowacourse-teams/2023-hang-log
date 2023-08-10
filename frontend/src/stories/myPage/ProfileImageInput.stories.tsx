import { images } from '@mocks/data/image';
import type { Meta, StoryObj } from '@storybook/react';

import ProfileImageInput from '@components/myPage/EditUserProfileForm/ProfileImageInput/ProfileImageInput';

const meta = {
  title: 'myPage/ProfileImageInput',
  component: ProfileImageInput,
  args: {
    imageUrl: images[0],
  },
} satisfies Meta<typeof ProfileImageInput>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
