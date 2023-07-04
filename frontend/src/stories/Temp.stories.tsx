import type { Meta, StoryObj } from '@storybook/react';

const Temp = () => {
  return <div>Hello World</div>;
};

const meta = {
  title: 'Common/Temp',
  component: Temp,
} satisfies Meta<typeof Temp>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Success: Story = {};
