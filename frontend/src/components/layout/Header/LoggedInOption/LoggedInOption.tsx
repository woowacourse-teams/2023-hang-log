/* eslint-disable jsx-a11y/no-noninteractive-element-interactions */
import { PATH } from '@constants/path';
import { userInfo } from '@mocks/data/member';
import { Menu, MenuItem, MenuList, useOverlay } from 'hang-log-design-system';
import type { KeyboardEvent } from 'react';
import { useNavigate } from 'react-router-dom';

import {
  imageStyling,
  menuListStyling,
} from '@components/layout/Header/LoggedInOption/LoggedInOption.style';

const LoggedInOption = () => {
  const navigate = useNavigate();

  const { isOpen: isMenuOpen, open: openMenu, close: closeMenu } = useOverlay();

  const handleUserImageEnterKeyPress = (event: KeyboardEvent<HTMLImageElement>) => {
    if (event.key === 'Enter') {
      openMenu();
    }
  };

  const goToTargetPage = (path: string) => () => {
    navigate(path);
    closeMenu();
  };

  return (
    <Menu closeMenu={closeMenu}>
      <img
        css={imageStyling}
        src={userInfo.imageUrl}
        // eslint-disable-next-line jsx-a11y/no-noninteractive-tabindex
        tabIndex={0}
        alt="유저 프로필 이미지"
        onClick={openMenu}
        onKeyDown={handleUserImageEnterKeyPress}
      />
      {isMenuOpen && (
        <MenuList css={menuListStyling}>
          <MenuItem onClick={goToTargetPage(PATH.MY_PAGE)}>마이페이지</MenuItem>
          <MenuItem onClick={goToTargetPage(PATH.ROOT)}>로그아웃</MenuItem>
        </MenuList>
      )}
    </Menu>
  );
};

export default LoggedInOption;
