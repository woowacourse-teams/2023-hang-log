import type { Meta, StoryObj } from '@storybook/react';

import TripCreateForm from '@components/trip/TripCreateForm/TripCreateForm';

const meta = {
  title: 'trip/TripCreateForm',
  component: TripCreateForm,
  decorators: [
    (Story) => (
      <div css={{ width: '400px' }}>
        <Story />
      </div>
    ),
  ],
} satisfies Meta<typeof TripCreateForm>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
