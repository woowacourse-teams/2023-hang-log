import type { Meta, StoryObj } from '@storybook/react';

import Button from '@components/Button/Button';
import Menu from '@components/Menu/Menu';
import MenuItem from '@components/MenuItem/MenuItem';
import MenuList from '@components/MenuList/MenuList';

import { useOverlay } from '..';

const meta = {
  title: 'Menu',
  component: Menu,
  args: {},
} satisfies Meta<typeof Menu>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {
  render: () => {
    const { isOpen, toggle, close } = useOverlay();

    return (
      <Menu closeMenu={close}>
        <Button onClick={toggle}>Menu</Button>
        {isOpen && (
          <MenuList>
            <MenuItem onClick={close}>로그인</MenuItem>
            <MenuItem onClick={close}>로그아웃</MenuItem>
          </MenuList>
        )}
      </Menu>
    );
  },
};
