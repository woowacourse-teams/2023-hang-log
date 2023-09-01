import type { Meta, StoryObj } from '@storybook/react';

import TripInformation from '@components/common/TripInformation/TripInformation';

import { useTripQuery } from '@hooks/api/useTripQuery';

const meta = {
  title: 'common/TripInformation',
  component: TripInformation,
  args: { tripId: 1 },
  decorators: [
    (Story) => {
      useTripQuery(1);

      return <Story />;
    },
  ],
} satisfies Meta<typeof TripInformation>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
