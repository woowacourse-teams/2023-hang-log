import LogoHorizontal from '@assets/svg/logo-horizontal.svg';
import { PATH } from '@constants/path';
import { Flex, Menu, MenuItem, MenuList, useOverlay } from 'hang-log-design-system';
import { useNavigate } from 'react-router-dom';

import { headerStyling, imageStyling } from '@components/layout/Header/Header.style';

const Header = () => {
  const navigate = useNavigate();
  const { isOpen, open, close } = useOverlay();

  return (
    <header css={headerStyling}>
      <Flex styles={{ justify: 'space-between', align: 'center' }}>
        <LogoHorizontal onClick={() => navigate(PATH.ROOT)} />
        <Menu closeMenu={close}>
          <div css={imageStyling} onClick={open}></div>
          {isOpen && (
            <MenuList>
              <MenuItem onClick={() => navigate(PATH.ROOT)}>마이 페이지</MenuItem>
              <MenuItem onClick={() => navigate(PATH.ROOT)}>로그아웃</MenuItem>
            </MenuList>
          )}
        </Menu>
      </Flex>
    </header>
  );
};

export default Header;
