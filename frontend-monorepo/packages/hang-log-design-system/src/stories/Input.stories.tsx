import SearchIcon from '@assets/svg/search-icon.svg';
import { containerStyle, informationStyle } from '@stories/styles';
import type { Meta, StoryObj } from '@storybook/react';

import Input from '@components/Input/Input';

const meta = {
  title: 'Input',
  component: Input,
  argTypes: {
    label: {
      control: { type: 'text' },
    },
    variant: {
      control: { type: 'radio' },
      options: ['default', 'text'],
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
    variant: 'default',
    size: 'medium',
    placeholder: 'placeholder',
    isError: false,
    required: false,
    id: 'input',
  },
} satisfies Meta<typeof Input>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Playground: Story = {};

export const Variants: Story = {
  render: ({ size, isError, placeholder }) => {
    return (
      <ul css={containerStyle}>
        <li css={informationStyle}>
          <h6>Default</h6>
          <Input size={size} isError={isError} placeholder={placeholder} />
        </li>
        <li css={informationStyle}>
          <h6>Text</h6>
          <Input variant="text" size={size} isError={isError} placeholder={placeholder} />
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
  render: ({ variant, isError, placeholder }) => {
    return (
      <ul css={containerStyle}>
        <li css={informationStyle}>
          <h6>Small</h6>
          <Input size="small" variant={variant} isError={isError} placeholder={placeholder} />
        </li>
        <li css={informationStyle}>
          <h6>Medium</h6>
          <Input variant={variant} isError={isError} placeholder={placeholder} />
        </li>
        <li css={informationStyle}>
          <h6>Large</h6>
          <Input size="large" variant={variant} isError={isError} placeholder={placeholder} />
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

export const Default: Story = {
  args: {
    variant: 'default',
  },
  argTypes: {
    variant: {
      control: false,
    },
  },
};

export const Text: Story = {
  args: {
    variant: 'text',
  },
  argTypes: {
    variant: {
      control: false,
    },
  },
};

export const WithIcon: Story = {
  args: {
    icon: <SearchIcon />,
  },
  argTypes: {
    icon: {
      control: false,
    },
  },
  name: 'Input with Icon',
};

export const WithLabel: Story = {
  args: {
    label: 'Label',
  },
  name: 'Input with Label',
};

export const WithSupportingText: Story = {
  args: {
    supportingText: 'Supporting Text',
  },
  name: 'Input with Supporting Text',
};

export const WithLabelAndSupportingText: Story = {
  args: {
    label: 'Label',
    supportingText: 'Supporting Text',
    required: true,
  },
  name: 'Input with Label and Supporting Text',
};

export const WithIconLabelAndSupportingText: Story = {
  args: {
    label: 'Label',
    icon: <SearchIcon />,
    supportingText: 'Supporting Text',
    required: true,
  },
  argTypes: {
    icon: {
      control: false,
    },
  },
  name: 'Input with Icon Label, Supporting Text',
};
