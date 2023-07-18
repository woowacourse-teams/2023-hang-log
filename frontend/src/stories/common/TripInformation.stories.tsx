import { trip } from '@mocks/data/trip';
import type { Meta, StoryObj } from '@storybook/react';

import TripInformation from '@components/common/TripInformation/TripInformation';

const meta = {
  title: 'common/TripInformation',
  component: TripInformation,
  args: { ...trip },
} satisfies Meta<typeof TripInformation>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
