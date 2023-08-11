import LogoHorizontal from '@assets/svg/logo-horizontal.svg';
import { PATH } from '@constants/path';
import { isLoggedInState } from '@store/auth';
import { Flex } from 'hang-log-design-system';
import { Suspense } from 'react';
import { useNavigate } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

import { getItemStyling, headerStyling } from '@components/layout/Header/Header.style';
import LoggedInOption from '@components/layout/Header/LoggedInMenu/LoggedInMenu';
import LoggedOutOption from '@components/layout/Header/LoggedOutMenu/LoggedOutMenu';

const Header = () => {
  const navigate = useNavigate();

  const isLoggedIn = useRecoilValue(isLoggedInState);

  return (
    <header css={headerStyling}>
      <Flex styles={{ justify: 'space-between', align: 'center' }}>
        <LogoHorizontal
          css={getItemStyling(isLoggedIn)}
          tabIndex={0}
          aria-label="행록 로고"
          onClick={() => navigate(PATH.ROOT)}
        />
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
