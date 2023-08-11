import { useNavigate } from 'react-router-dom';

import { Button, Flex, Theme } from 'hang-log-design-system';

import { getItemStyling } from '@components/layout/Header/Header.style';

import { PATH } from '@constants/path';

const LoggedOutMenu = () => {
  const navigate = useNavigate();

  return (
    <Flex styles={{ gap: Theme.spacer.spacing1 }} css={getItemStyling(true)}>
      <Button type="button" variant="secondary" size="small" onClick={() => navigate(PATH.LOGIN)}>
        로그인
      </Button>
      <Button type="button" variant="primary" size="small" onClick={() => navigate(PATH.SIGN_UP)}>
        회원가입
      </Button>
    </Flex>
  );
};

export default LoggedOutMenu;
