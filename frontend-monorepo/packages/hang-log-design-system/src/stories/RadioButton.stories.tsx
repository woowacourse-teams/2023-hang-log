import type { Meta, StoryObj } from '@storybook/react';

import RadioButton from '@components/RadioButton/RadioButton';

const meta = {
  title: 'RadioButton',
  component: RadioButton,
  args: {
    options: ['option1', 'option2'],
    name: 'sample',
  },
} satisfies Meta<typeof RadioButton>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Playground: Story = {
  args: {
    label: 'Label',
    supportingText: 'Supporting text',
  },
};

export const Default: Story = {};

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
