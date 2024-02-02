/* eslint-disable jsx-a11y/tabindex-no-positive */
import { Suspense } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

import { useRecoilValue } from 'recoil';

import { Flex, Text, Theme } from 'hang-log-design-system';

import {
  getItemStyling,
  getTapNavigateButtonStyling,
  headerStyling,
} from '@components/layout/Header/Header.style';
import LoggedOutOption from '@components/layout/Header/LoggedOutMenu/LoggedOutMenu';

import { isLoggedInState } from '@store/auth';

import { PATH } from '@constants/path';

import LogoHorizontal from '@assets/svg/logo-horizontal.svg?react';

const Header = () => {
  const navigate = useNavigate();
  const location = useLocation().pathname;

  // const isLoggedIn = useRecoilValue(isLoggedInState);
  const isLoggedIn = false;

  return (
    <header css={headerStyling}>
      <Flex styles={{ justify: 'space-between', align: 'center' }}>
        <Flex styles={{ align: 'center', gap: Theme.spacer.spacing4 }}>
          <LogoHorizontal
            css={getItemStyling(isLoggedIn)}
            tabIndex={0}
            aria-label="행록 로고"
            onClick={() => navigate(PATH.ROOT)}
          />
        </Flex>
        <LoggedOutOption />
      </Flex>
    </header>
  );
};

export default Header;
