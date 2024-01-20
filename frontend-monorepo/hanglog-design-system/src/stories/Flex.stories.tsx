import type { Meta, StoryObj } from '@storybook/react';

import Box from '@components/Box/Box';
import Flex from '@components/Flex/Flex';

import { Theme } from '@styles/Theme';

const meta = {
  title: 'Flex',
  component: Flex,
} satisfies Meta<typeof Flex>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Playground: Story = {
  render: (args) => {
    return (
      <Flex styles={args.styles}>
        <Box
          styles={{
            width: '100px',
            height: '60px',
            backgroundColor: Theme.color.red300,
            borderRadius: '5px',
          }}
        >
          <div>box1</div>
        </Box>
        <Box
          styles={{
            width: '100px',
            height: '60px',
            backgroundColor: Theme.color.gray300,
            borderRadius: '5px',
          }}
        >
          <div>box2</div>
        </Box>
        <Box
          styles={{
            width: '100px',
            height: '60px',
            backgroundColor: Theme.color.blue500,
            borderRadius: '5px',
          }}
        >
          <div>box3</div>
        </Box>
      </Flex>
    );
  },
  args: {
    styles: {
      width: '800px',
      height: '500px',
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
