import type { Meta, StoryObj } from '@storybook/react';

import Box from '@components/Box/Box';
import Center from '@components/Center/Center';

import { Theme } from '..';

const meta = {
  title: 'Center',
  component: Center,
} satisfies Meta<typeof Center>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Playground: Story = {
  render: () => {
    return (
      <Box
        styles={{
          width: '500px',
          backgroundColor: Theme.color.gray100,
          padding: Theme.spacer.spacing4,
        }}
      >
        <Center>
          <div
            css={{
              width: '100px',
              backgroundColor: Theme.color.blue200,
              padding: Theme.spacer.spacing3,
            }}
          >
            Centered Box
          </div>
        </Center>
      </Box>
    );
  },
};
