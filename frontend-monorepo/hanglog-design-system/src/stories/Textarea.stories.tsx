import { containerStyle, informationStyle } from '@stories/styles';
import type { Meta, StoryObj } from '@storybook/react';

import Textarea from '@components/Textarea/Textarea';

const meta = {
  title: 'Textarea',
  component: Textarea,
  argTypes: {
    label: {
      control: { type: 'text' },
    },
    size: {
      control: { type: 'radio' },
      options: ['small', 'medium', 'large'],
    },
    isError: {
      control: { type: 'boolean' },
    },
    supportingText: {
      control: { type: 'text' },
    },
    required: {
      control: { type: 'boolean' },
    },
  },
  args: {
    size: 'medium',
    placeholder: 'placeholder',
    isError: false,
    required: false,
  },
} satisfies Meta<typeof Textarea>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Playground: Story = {};

export const Sizes: Story = {
  render: ({ isError, placeholder }) => {
    return (
      <ul css={containerStyle}>
        <li css={informationStyle}>
          <h6>Small</h6>
          <Textarea size="small" isError={isError} placeholder={placeholder} />
        </li>
        <li css={informationStyle}>
          <h6>Medium</h6>
          <Textarea isError={isError} placeholder={placeholder} />
        </li>
        <li css={informationStyle}>
          <h6>Large</h6>
          <Textarea size="large" isError={isError} placeholder={placeholder} />
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

export const WithLabel: Story = {
  args: {
    label: 'Label',
  },
  name: 'Textarea with Label',
};

export const WithSupportingText: Story = {
  args: {
    supportingText: 'Supporting Text',
  },
  name: 'Textarea with Supporting Text',
};

export const WithLabelAndSupportingText: Story = {
  args: {
    label: 'Label',
    supportingText: 'Supporting Text',
    required: true,
  },
  name: 'Textarea with Label and Supporting Text',
};
