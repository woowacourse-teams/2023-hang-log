import LogoHorizontal from '@assets/svg/logo-horizontal.svg';
import { PATH } from '@constants/path';
import { Flex, Menu, MenuItem, MenuList, useOverlay } from 'hang-log-design-system';
import type { KeyboardEvent } from 'react';
import { useNavigate } from 'react-router-dom';

import {
  headerStyling,
  imageStyling,
  menuListStyling,
} from '@components/layout/Header/Header.style';

const Header = () => {
  const navigate = useNavigate();
  const { isOpen, open, close } = useOverlay();

  const handleUserImageEnterKeyPress = (event: KeyboardEvent<HTMLButtonElement>) => {
    if (event.key === 'Enter') {
      open();
    }
  };

  return (
    <header css={headerStyling}>
      <Flex styles={{ justify: 'space-between', align: 'center' }}>
        <LogoHorizontal tabIndex={0} onClick={() => navigate(PATH.ROOT)} />
        <Menu closeMenu={close}>
          <button
            type="button"
            css={imageStyling}
            aria-label="유저 프로필 이미지"
            onClick={open}
            onKeyDown={handleUserImageEnterKeyPress}
          />
          {isOpen && (
            <MenuList css={menuListStyling}>
              <MenuItem onClick={() => navigate(PATH.ROOT)}>마이페이지</MenuItem>
              <MenuItem onClick={() => navigate(PATH.ROOT)}>로그아웃</MenuItem>
            </MenuList>
          )}
        </Menu>
      </Flex>
    </header>
  );
};

export default Header;
