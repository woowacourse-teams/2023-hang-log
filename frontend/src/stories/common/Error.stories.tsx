import type { Meta, StoryObj } from '@storybook/react';

import Error from '@components/common/Error/Error';

const meta = {
  title: 'common/Error',
  component: Error,
  argTypes: {
    statusCode: { control: 'radio', options: [400, 404, 500] },
  },
  args: {
    statusCode: 404,
  },
} satisfies Meta<typeof Error>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Error404: Story = {
  name: '404 Error',
};

export const Error400: Story = {
  name: '400 Error',
  args: {
    statusCode: 400,
  },
};

export const Error500: Story = {
  name: '500 Error',
  args: {
    statusCode: 500,
  },
};
