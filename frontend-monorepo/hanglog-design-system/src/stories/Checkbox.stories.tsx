import { containerStyle, informationStyle } from '@stories/styles';
import type { Meta, StoryObj } from '@storybook/react';

import Checkbox from '@components/Checkbox/Checkbox';

const meta = {
  title: 'Checkbox',
  component: Checkbox,
  argTypes: {
    checked: {
      control: { type: 'boolean' },
    },
    label: {
      control: { type: 'text' },
    },
  },
  args: {
    checked: true,
    label: 'Label',
  },
} satisfies Meta<typeof Checkbox>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Playground: Story = {};

export const Checkboxes: Story = {
  render: () => {
    return (
      <ul css={containerStyle}>
        <li css={informationStyle}>
          <h6>Checked</h6>
          <Checkbox isChecked />
        </li>
        <li css={informationStyle}>
          <h6>Unchecked</h6>
          <Checkbox />
        </li>
        <li css={informationStyle}>
          <h6>Checked with Label</h6>
          <Checkbox checked label="Label" isChecked />
        </li>
        <li css={informationStyle}>
          <h6>Unchecked with Label</h6>
          <Checkbox label="Label" />
        </li>
      </ul>
    );
  },
  argTypes: {
    checked: {
      control: false,
    },
    label: {
      control: false,
    },
  },
};
