import { containerStyle } from '@stories/styles';
import type { Meta, StoryObj } from '@storybook/react';

import NewToggle from '@components/NewToggle/NewToggle';

const meta = {
  title: 'NewToggle',
  component: NewToggle,
  args: {
    initialSelect: 'toggle1',
  },
  argTypes: {
    initialSelect: { control: 'string' },
  },
  decorators: [
    (Story) => (
      <ul css={containerStyle}>
        <Story />
      </ul>
    ),
  ],
} satisfies Meta<typeof NewToggle>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {
  render: ({ ...args }) => (
    <NewToggle
      {...args}
      additinalFunc={(id: number | string) => {
        // eslint-disable-next-line no-console
        console.log(id);
      }}
    >
      <h6>NewToggle</h6>
      <div style={{ display: 'flex' }}>
        <NewToggle.List text="Toggle 1" toggleKey="toggle1" />
        <NewToggle.List text="Toggle 2" toggleKey="toggle2" />
        <NewToggle.List text="Toggle 3" toggleKey="toggle3" />
      </div>
      <div>
        <NewToggle.Item toggleKey="toggle1">나는토글1</NewToggle.Item>
        <NewToggle.Item toggleKey="toggle2">나는토글2</NewToggle.Item>
        <NewToggle.Item toggleKey="toggle3">나는토글3</NewToggle.Item>
      </div>
    </NewToggle>
  ),
};
