/* eslint-disable jsx-a11y/no-noninteractive-element-interactions */
import type { KeyboardEvent } from 'react';
import { useNavigate } from 'react-router-dom';

import { Menu, MenuItem, MenuList, useOverlay } from 'hang-log-design-system';

import {
  imageStyling,
  menuListStyling,
} from '@components/layout/Header/LoggedInMenu/LoggedInMenu.style';

import { useLogOutMutation } from '@hooks/api/useLogOutMutation';
import { useUserInfoQuery } from '@hooks/api/useUserInfoQuery';

import { ACCESS_TOKEN_KEY } from '@constants/api';
import { PATH } from '@constants/path';

const LoggedInMenu = () => {
  const navigate = useNavigate();

  const { userInfoData } = useUserInfoQuery();
  const logOutMutation = useLogOutMutation();

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

  const handleLogOut = () => {
    logOutMutation.mutate({ accessToken: localStorage.getItem(ACCESS_TOKEN_KEY) ?? '' });
    closeMenu();
  };

  return (
    <Menu closeMenu={closeMenu}>
      <img
        css={imageStyling}
        src={userInfoData.imageUrl}
        // eslint-disable-next-line jsx-a11y/no-noninteractive-tabindex
        tabIndex={0}
        alt="유저 프로필 이미지"
        onClick={openMenu}
        onKeyDown={handleUserImageEnterKeyPress}
      />
      {isMenuOpen && (
        <MenuList css={menuListStyling}>
          <MenuItem onClick={goToTargetPage(PATH.MY_PAGE)}>마이페이지</MenuItem>
          <MenuItem onClick={handleLogOut}>로그아웃</MenuItem>
        </MenuList>
      )}
    </Menu>
  );
};

export default LoggedInMenu;
