import { containerStyle, informationStyle } from '@stories/styles';
import type { Meta, StoryObj } from '@storybook/react';

import type { TextProps } from '@components/Text/Text';
import Text from '@components/Text/Text';

const meta = {
  title: 'Text',
  component: Text,
  argTypes: {
    size: {
      control: { type: 'radio' },
      options: ['xSmall', 'small', 'medium', 'large'],
    },
    children: {
      control: { type: 'text' },
    },
  },
  args: {
    size: 'medium',
    children: 'Text',
  },
} satisfies Meta<typeof Text>;

export default meta;
type Story = StoryObj<typeof meta>;

const createTextStory = (size: TextProps['size']) => ({
  args: {
    size,
  },
  argTypes: {
    size: {
      control: false,
    },
  },
});

export const Playground: Story = {};

export const Sizes: Story = {
  render: ({ children }) => {
    return (
      <ul css={containerStyle}>
        <li css={informationStyle}>
          <h6>X Small</h6>
          <Text size="xSmall">{children}</Text>
        </li>
        <li css={informationStyle}>
          <h6>Small</h6>
          <Text size="small">{children}</Text>
        </li>
        <li css={informationStyle}>
          <h6>Medium</h6>
          <Text size="medium">{children}</Text>
        </li>
        <li css={informationStyle}>
          <h6>Large</h6>
          <Text size="large">{children}</Text>
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

export const XSmall: Story = createTextStory('xSmall');

export const Small: Story = createTextStory('small');

export const Medium: Story = createTextStory('medium');

export const Large: Story = createTextStory('large');
