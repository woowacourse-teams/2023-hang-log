import { containerStyle } from '@stories/styles';
import type { Meta, StoryObj } from '@storybook/react';

import Box from '@components/Box/Box';

import { Theme } from '@styles/Theme';

const meta = {
  title: 'Box',
  component: Box,
  decorators: [
    (Story) => (
      <ul css={containerStyle}>
        <Story />
      </ul>
    ),
  ],
} satisfies Meta<typeof Box>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Playground: Story = {
  render: (args) => {
    return (
      <Box styles={args.styles}>
        <div>box1</div>
      </Box>
    );
  },
  args: {
    styles: {
      backgroundColor: Theme.color.blue500,
      borderRadius: '5px',
    },
  },
  argTypes: {
    styles: {
      control: {
        type: 'object',
      },
    },
  },
};
