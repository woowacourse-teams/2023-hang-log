/* eslint-disable jsx-a11y/tabindex-no-positive */
import { Suspense } from 'react';
import { useNavigate } from 'react-router-dom';

import { useRecoilValue } from 'recoil';

import { Flex, Theme } from 'hang-log-design-system';

import {
  getItemStyling,
  getTapNavigateButtonStyling,
  headerStyling,
} from '@components/layout/Header/Header.style';
import LoggedInOption from '@components/layout/Header/LoggedInMenu/LoggedInMenu';
import LoggedOutOption from '@components/layout/Header/LoggedOutMenu/LoggedOutMenu';

import { isLoggedInState } from '@store/auth';

import { PATH } from '@constants/path';

import LogoHorizontal from '@assets/svg/logo-horizontal.svg';
import TextCommunity from '@assets/svg/text-community.svg';
import TextMyTrip from '@assets/svg/text-mytrip.svg';

const Header = () => {
  const navigate = useNavigate();

  const isLoggedIn = useRecoilValue(isLoggedInState);

  return (
    <header css={headerStyling}>
      <Flex styles={{ justify: 'space-between', align: 'center' }}>
        <Flex styles={{ align: 'center', gap: Theme.spacer.spacing5 }}>
          <LogoHorizontal
            css={getItemStyling(isLoggedIn)}
            tabIndex={0}
            aria-label="행록 로고"
            onClick={() => navigate(PATH.ROOT)}
          />
          <TextCommunity
            css={getTapNavigateButtonStyling(isLoggedIn)}
            tabIndex={1}
            aria-label="커뮤니티 페이지 이동 버튼"
          />
          <TextMyTrip
            css={getTapNavigateButtonStyling(isLoggedIn)}
            tabIndex={2}
            aria-label="내 여행 페이지 이동 버튼"
          />
        </Flex>
        {isLoggedIn ? (
          <Suspense>
            <LoggedInOption />
          </Suspense>
        ) : (
          <LoggedOutOption />
        )}
      </Flex>
    </header>
  );
};

export default Header;
