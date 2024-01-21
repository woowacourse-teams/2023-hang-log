import { containerStyle, informationStyle } from '@stories/styles';
import type { Meta, StoryObj } from '@storybook/react';

import Divider from '@components/Divider/Divider';

const meta = {
  title: 'Divider',
  component: Divider,
  argTypes: {
    direction: {
      control: { type: 'radio' },
      options: ['horizontal', 'vertical'],
    },
    length: {
      control: { type: 'text' },
    },
  },
  args: {
    direction: 'horizontal',
    length: '700px',
  },
} satisfies Meta<typeof Divider>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Playground: Story = {};

export const Directions: Story = {
  render: () => {
    return (
      <ul css={containerStyle}>
        <li css={informationStyle}>
          <h6>Horizontal</h6>
          <Divider />
        </li>
        <li css={informationStyle}>
          <h6>Vertical</h6>
          <Divider direction="vertical" length="200px" />
        </li>
      </ul>
    );
  },
  argTypes: {
    direction: {
      control: false,
    },
  },
};

export const Horizontal: Story = {
  argTypes: {
    direction: {
      control: false,
    },
  },
};

export const Vertical: Story = {
  args: {
    direction: 'vertical',
  },
  argTypes: {
    direction: {
      control: false,
    },
  },
};
