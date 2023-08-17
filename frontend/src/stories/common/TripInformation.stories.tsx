import type { Meta, StoryObj } from '@storybook/react';

import TripInformation from '@components/common/TripInformation/TripInformation';

import { trip } from '@mocks/data/trip';

const meta = {
  title: 'common/TripInformation',
  component: TripInformation,
  args: { ...trip },
} satisfies Meta<typeof TripInformation>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
