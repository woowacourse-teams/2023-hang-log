import { containerStyle, informationStyle } from '@stories/styles';
import type { Meta, StoryObj } from '@storybook/react';

import Badge from '@components/Badge/Badge';

const meta = {
  title: 'Badge',
  component: Badge,
  argTypes: {
    variant: {
      control: { type: 'radio' },
      options: ['default', 'primary', 'outline'],
      description: 'Badge의 비주얼 스타일',
    },
    children: {
      control: { type: 'text' },
      description: 'Badge에 표시할 내용',
    },
  },
  args: {
    variant: 'default',
    children: 'Badge',
  },
} satisfies Meta<typeof Badge>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Playground: Story = {};

export const Variants: Story = {
  render: ({ children }) => {
    return (
      <ul css={containerStyle}>
        <li css={informationStyle}>
          <h6>Default</h6>
          <Badge variant="default">{children}</Badge>
        </li>
        <li css={informationStyle}>
          <h6>Primary</h6>
          <Badge variant="primary">{children}</Badge>
        </li>
        <li css={informationStyle}>
          <h6>Outline</h6>
          <Badge variant="outline">{children}</Badge>
        </li>
      </ul>
    );
  },
  argTypes: {
    variant: {
      control: false,
    },
  },
};

export const Default: Story = {
  argTypes: {
    variant: {
      control: false,
    },
  },
};

export const Primary: Story = {
  args: {
    variant: 'primary',
  },
  argTypes: {
    variant: {
      control: false,
    },
  },
};

export const Outline: Story = {
  args: {
    variant: 'outline',
  },
  argTypes: {
    variant: {
      control: false,
    },
  },
};
