import { containerStyle, informationStyle } from '@stories/styles';
import type { Meta, StoryObj } from '@storybook/react';
import { ChangeEvent } from 'react';

import SwitchToggle from '@components/SwitchToggle/SwitchToggle';

const meta = {
  title: 'SwitchToggle',
  component: SwitchToggle,
  args: {
    onChange: (e: ChangeEvent<HTMLInputElement>) => {},
    checkedState: false,
  },
} satisfies Meta<typeof SwitchToggle>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Playground: Story = {};
