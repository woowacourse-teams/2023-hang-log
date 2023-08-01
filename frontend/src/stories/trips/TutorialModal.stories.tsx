import type { Meta, StoryObj } from '@storybook/react';

import TutorialModal from '@components/trips/TutorialModal/TutorialModal';

const meta = {
  title: 'trips/TutorialModal',
  component: TutorialModal,
} satisfies Meta<typeof TutorialModal>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
