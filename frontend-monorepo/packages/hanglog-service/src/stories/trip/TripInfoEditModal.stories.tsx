import type { Meta, StoryObj } from '@storybook/react';

import TripInfoEditModal from '@components/trip/TripInfoEditModal/TripInfoEditModal';

import { trip } from '@mocks/data/trip';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
const { dayLogs, ...information } = trip;

const meta = {
  title: 'trip/TripInfoEditModal',
  component: TripInfoEditModal,
  args: {
    isOpen: true,
    onClose: () => {},
    ...information,
  },
} satisfies Meta<typeof TripInfoEditModal>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
