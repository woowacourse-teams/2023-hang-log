import { containerStyle, informationStyle } from '@stories/styles';
import type { Meta, StoryObj } from '@storybook/react';

import type { FloatingButtonProps } from '@components/FloatingButton/FloatingButton';
import FloatingButton from '@components/FloatingButton/FloatingButton';

const meta = {
  title: 'FloatingButton',
  component: FloatingButton,
  argTypes: {
    variant: {
      control: { type: 'radio' },
      options: ['default', 'primary'],
    },
    size: {
      control: { type: 'radio' },
      options: ['small', 'medium'],
    },
  },
  args: {
    variant: 'primary',
    size: 'medium',
  },
} satisfies Meta<typeof FloatingButton>;

export default meta;
type Story = StoryObj<typeof meta>;

const createButtonStory = (variant: FloatingButtonProps['variant']) => ({
  args: {
    variant,
  },
  argTypes: {
    variant: {
      control: false,
    },
  },
});

export const Playground: Story = {};

export const Variants: Story = {
  render: ({ size }) => {
    return (
      <ul css={containerStyle}>
        <li css={informationStyle}>
          <h6>Primary</h6>
          <FloatingButton variant="primary" size={size} />
        </li>
        <li css={informationStyle}>
          <h6>Default</h6>
          <FloatingButton variant="default" size={size} />
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

export const Sizes: Story = {
  render: ({ variant }) => {
    return (
      <ul css={containerStyle}>
        <li css={informationStyle}>
          <h6>Small</h6>
          <FloatingButton variant={variant} size="small" />
        </li>
        <li css={informationStyle}>
          <h6>Medium</h6>
          <FloatingButton variant={variant} size="medium" />
        </li>
      </ul>
    );
  },
  argTypes: {
    size: {
      control: false,
    },
  },
};

export const Default: Story = createButtonStory('default');

export const Primary: Story = createButtonStory('primary');
