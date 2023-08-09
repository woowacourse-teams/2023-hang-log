import LogoHorizontal from '@assets/svg/logo-horizontal.svg';
import { PATH } from '@constants/path';
import { isLoggedInState } from '@store/auth';
import { Button, Flex, Menu, MenuItem, MenuList, Theme, useOverlay } from 'hang-log-design-system';
import type { KeyboardEvent } from 'react';
import { useNavigate } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

import {
  buttonContainerStyling,
  headerStyling,
  imageStyling,
  menuListStyling,
} from '@components/layout/Header/Header.style';

const Header = () => {
  const navigate = useNavigate();

  const isLoggedIn = useRecoilValue(isLoggedInState);

  const { isOpen: isMenuOpen, open: openMenu, close: closeMenu } = useOverlay();

  const handleUserImageEnterKeyPress = (event: KeyboardEvent<HTMLButtonElement>) => {
    if (event.key === 'Enter') {
      openMenu();
    }
  };

  const goToTargetPage = (path: string) => () => {
    navigate(path);
    closeMenu();
  };

  return (
    <header css={headerStyling}>
      <Flex styles={{ justify: 'space-between', align: 'center' }}>
        <LogoHorizontal tabIndex={0} onClick={() => navigate(PATH.ROOT)} />
        {isLoggedIn ? (
          <Menu closeMenu={closeMenu}>
            <button
              type="button"
              css={imageStyling}
              aria-label="유저 프로필 이미지"
              onClick={openMenu}
              onKeyDown={handleUserImageEnterKeyPress}
            />
            {isMenuOpen && (
              <MenuList css={menuListStyling}>
                <MenuItem onClick={goToTargetPage(PATH.ROOT)}>마이페이지</MenuItem>
                <MenuItem onClick={goToTargetPage(PATH.ROOT)}>로그아웃</MenuItem>
              </MenuList>
            )}
          </Menu>
        ) : (
          <Flex styles={{ gap: Theme.spacer.spacing1 }} css={buttonContainerStyling}>
            <Button
              type="button"
              variant="secondary"
              size="small"
              onClick={() => navigate(PATH.LOGIN)}
            >
              로그인
            </Button>
            <Button
              type="button"
              variant="primary"
              size="small"
              onClick={() => navigate(PATH.SIGN_UP)}
            >
              회원가입
            </Button>
          </Flex>
        )}
      </Flex>
    </header>
  );
};

export default Header;
